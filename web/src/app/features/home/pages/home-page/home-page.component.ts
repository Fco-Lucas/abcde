import { Component, inject, signal, ViewChild, OnInit, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';

import { LotFiltersComponent, LotFiltersFormValues } from '../../components/lot-filters/lot-filters.component';
import { LotListComponent } from '../../components/lot-list/lot-list.component';
import { DialogCreateLotComponent, DialogCreateLotData } from '../../components/dialog-create-lot/dialog-create-lot.component';
import { DialogImageUploadProgressComponent, DialogImageUploadProgressData } from '../../components/dialog-image-upload-progress/dialog-image-upload-progress.component';
import { AuthService, AuthenticatedUserRole } from '../../../../core/services/auth.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LotInterface } from '../../models/lot.model';
import { PermissionsService } from '../../../permissions/services/permissions.service';
import { PermissionInterface } from '../../../permissions/models/permission.model';
import { PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';
import { LotService } from '../../services/lot.service';
import { catchError, combineLatest, firstValueFrom, of, shareReplay, switchMap, tap } from 'rxjs';
import { LotStateService } from '../../services/lot-state.service';
import { Router } from '@angular/router';
import { UiNotFoundComponent } from '../../../../shared/components/ui-not-found/ui-not-found.component';
import { ClientService } from '../../../clients/services/client.service';
import type { Client } from '../../../clients/models/client.model';
import { LoadingService } from '../../../../core/services/loading.service';

interface HomeState {
  permissions: PermissionInterface | null;
  lots: LotInterface[];
  totalElements: number;
  loading: boolean;
  error: string | null;
}

interface HomeLotsQuery {
  filters: Partial<LotFiltersFormValues>;
  pagination: PageEvent;
  reload: number;
}

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    LotFiltersComponent,
    LotListComponent,
    MatProgressSpinnerModule,
    UiErrorComponent,
    UiNotFoundComponent
  ],
  templateUrl: './home-page.component.html',
})
export class HomePageComponent {
  private readonly dialog = inject(MatDialog);
  private lotService = inject(LotService);
  private lotStateService = inject(LotStateService);
  private authService = inject(AuthService);
  private clientService = inject(ClientService);
  private permissionsService = inject(PermissionsService);
  private notification = inject(NotificationService);
  private loader = inject(LoadingService);
  private router = inject(Router);

  private query = signal<HomeLotsQuery>({
    filters: {},
    pagination: { pageIndex: 0, pageSize: 10, length: 0 },
    reload: 0,
  });

  private state = signal<HomeState>({
    permissions: null,
    lots: [],
    totalElements: 0,
    loading: true,
    error: null,
  });

  public readonly permissions = computed(() => this.state().permissions);
  public readonly lots = computed(() => this.state().lots);
  public readonly totalElements = computed(() => this.state().totalElements);
  public readonly isLoading = computed(() => this.state().loading);
  public readonly error = computed(() => this.state().error);
  public readonly pagination = computed(() => this.query().pagination);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoading() && this.lots().length === 0) return 'loading';
    if (this.error()) return 'error';
    return 'success';
  });

  public readonly userId = toSignal(this.authService.currentUserId$);
  public readonly userRole = toSignal(this.authService.currentUserRole$);

  constructor() {
    // Stream para dados iniciais (que não mudam com filtros)
    const initialData$ = this.authService.currentUserId$.pipe(
      switchMap(userId => {
        if (!userId) throw new Error("ID do usuário autenticado não encontrado.");
        return this.permissionsService.getUserPermission(userId);
      }),
      // shareReplay garante que este fluxo só executa UMA VEZ.
      shareReplay({ bufferSize: 1, refCount: true }) 
    );

    // Stream para as queries dinâmicas
    const query$ = toObservable(this.query);

    combineLatest([initialData$, query$]).pipe(
      tap(() => this.state.update(s => ({ ...s, loading: true, error: null }))),
      switchMap(([permissions, currentQuery]) => {
        // Atualiza as permissões no estado
        this.state.update(s => ({ ...s, permissions }));

        if(!permissions.read_files) {
          this.state.update(s => ({ ...s, lots: [], totalElements: 0, loading: false }))
          return of(null);
        }
        
        const { pagination, filters } = currentQuery;
        const filterStatus = filters.status === "ALL" ? "" : filters.status;
        return this.lotService.getAllLotsUserPageable(
          pagination.pageIndex, pagination.pageSize, filters.name, 
          filters.client, filters.clientUser, filterStatus
        ).pipe(
          catchError(err => {
            this.state.update(s => ({ ...s, loading: false, error: "Falha ao carregar a lista de lotes." }));
            return of(null);
          })
        );
      }),
      catchError(err => {
        this.state.update(s => ({ ...s, loading: false, error: err.message }));
        return of(null);
      })
    ).subscribe(response => {
      if (response) {
        this.state.update(s => ({
          ...s,
          lots: response.content,
          totalElements: response.totalElements,
          loading: false,
        }));
      }
    });
  }

  onFilterSubmit(filters: LotFiltersFormValues): void {
    this.query.update(q => ({ ...q, filters, pagination: { ...q.pagination, pageIndex: 0 } }));
  }

  onPageChange(event: PageEvent): void {
    this.query.update(q => ({ ...q, pagination: event }));
  }

  forceReload(): void {
    this.query.update(q => ({ ...q, reload: q.reload + 1 }));
  }

  openCreateLotDialog(): void {
    const currentUserId = this.userId();
    if (!currentUserId) {
      this.notification.showError("Não foi possível identificar o usuário para criar o lote.");
      return;
    }
    const dialogData: DialogCreateLotData = { userId: currentUserId };
    const createDialogRef = this.dialog.open(DialogCreateLotComponent, { width: '500px', data: dialogData });
    createDialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      const { lot, images } = result;
      if (images && images.length > 0) {
        this.openProgressDialog(lot, images);
      } else {
        this.notification.showSuccess("Lote criado com sucesso!");
        this.forceReload();
      }
    });
  }

  private openProgressDialog(lot: LotInterface, images: FileList): void {
    const dialogData: DialogImageUploadProgressData = { lotId: lot.id, images };
    this.dialog.open(DialogImageUploadProgressComponent, { width: '500px', data: dialogData, disableClose: true })
      .afterClosed().subscribe(() => this.forceReload());
  }

  private async getClientLotByCnpj(cnpj: string): Promise<Client | null> {
    try {
      return await firstValueFrom(this.clientService.getByCnpj(cnpj));
    } catch (err) {
      this.notification.showError("Ocorreu um erro ao buscar o cliente na qual pertence o lote");
      return null;
    }
  }

  async onOpenLotDetails(lot: LotInterface) {
    this.loader.showLoad("Buscando informações...");

    const permissions = this.permissions();
    if (!permissions) {
      this.notification.showError("Erro ao obter as permissões do usuário");
      this.loader.hideLoad();
      return;
    }

    const clientLot = await this.getClientLotByCnpj(lot.userCnpj);
    if (!clientLot) {
      this.notification.showError("Erro ao obter o cliente que criou o lote");
      this.loader.hideLoad();
      return;
    }

    this.lotStateService.selectLot(lot);
    this.lotStateService.setPermissions(permissions);
    this.lotStateService.setClientLot(clientLot);

    this.loader.hideLoad();
    this.router.navigate(["/app/loteDetails"]);
  }
}
