import { Component, computed, DestroyRef, effect, inject, signal } from '@angular/core';

import { MatIconModule } from '@angular/material/icon';
import { ClientFiltersComponent, ClientFiltersFormValues } from '../../components/client-filters/client-filters.component';
import { ClientListComponent } from '../../components/client-list/client-list.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { DialogCreateClientComponent } from '../../components/dialog-create-client/dialog-create-client.component';

import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { Client, ClientStatus } from '../../models/client.model';
import { ClientService } from '../../services/client.service';
import { ConfirmationDialogService } from '../../../../core/services/confirmation-dialog.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';
import { DialogUpdateClientComponent, DialogUpdateClientData, UpdateClientFormValues } from '../../components/dialog-update-client/dialog-update-client.component';
import { catchError, finalize, map, of, switchMap, tap } from 'rxjs';
import { takeUntilDestroyed, toObservable } from '@angular/core/rxjs-interop';
import { AuthService } from '../../../../core/services/auth.service';
import { ClientUsersService } from '../../services/client-users.service';
import { DialogUpdateComputexPostUrlComponent, type DialogUpdateComputexPostUrlData } from '../../components/dialog-update-computex-post-url/dialog-update-computex-post-url.component';
import { LoadingService } from '../../../../core/services/loading.service';

interface ClientsPageState {
  clients: Client[];
  totalElements: number;
  loading: boolean;
  error: string | null;
}

interface ClientQuery {
  filters: Partial<ClientFiltersFormValues>;
  pagination: PageEvent;
  reload: number;
}

@Component({
  selector: 'app-clients-page.component',
  imports: [
    CommonModule,
    ClientListComponent,
    ClientFiltersComponent,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    UiErrorComponent
  ],
  templateUrl: './clients-page.component.html',
})
export class ClientsPageComponent {
  private authService = inject(AuthService);
  private clientService = inject(ClientService);
  private confirmationDialogService = inject(ConfirmationDialogService);
  private notification = inject(NotificationService);
  private dialog = inject(MatDialog);
  private destroyRef = inject(DestroyRef);
  private loader = inject(LoadingService);

  private query = signal<ClientQuery>({
    filters: {},
    pagination: { pageIndex: 0, pageSize: 10, length: 0 },
    reload: 0,
  });

  private state = signal<ClientsPageState>({
    clients: [],
    totalElements: 0,
    loading: true,
    error: null,
  });

  // Signals COMPUTADOS públicos e `readonly` para a View
  public readonly clients = computed(() => this.state().clients);
  public readonly totalElements = computed(() => this.state().totalElements);
  public readonly isLoading = computed(() => this.state().loading);
  public readonly error = computed(() => this.state().error);
  public readonly pagination = computed(() => this.query().pagination);
  public readonly authClientCnpj = signal<string | null>(null);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoading() && this.clients().length === 0) return 'loading';
    if (this.error()) return 'error';
    return 'success';
  });

  public readonly isComputexClientUser = computed<boolean>(() => {
    const decoded = this.authService.getDecodedToken();
    if(!decoded) return false;
    const isCnpj = /^\d{14}$/.test(decoded.sub);
    return !isCnpj;
  });

  constructor() {
    effect(() => {
      if (this.isComputexClientUser() && this.authClientCnpj() === null) {
        const computex_cnpj: string = "12302493000101";
        this.clientService.getByCnpj(computex_cnpj).pipe(
          map(response => response.cnpj),
          catchError(err => {
            console.error("Erro ao buscar CNPJ do cliente do usuário autenticado:", err);
            this.notification.showError("Não foi possível obter os dados do seu usuário.");
            return of(null);
          }),
          takeUntilDestroyed(this.destroyRef)
        ).subscribe(cnpj => {
          if (cnpj) this.authClientCnpj.set(cnpj);
        });
      }
    });

    toObservable(this.query).pipe(
      tap(() => this.state.update(s => ({ ...s, loading: true, error: null }))),
      switchMap(currentQuery => {
        const statusFilter = currentQuery.filters.status === "ALL" ? "" : currentQuery.filters.status as ClientStatus;
        return this.clientService.getAllClientsPageable(
          currentQuery.pagination.pageIndex,
          currentQuery.pagination.pageSize,
          currentQuery.filters.cnpj,
          statusFilter
        ).pipe(
          catchError(err => {
            console.error("Erro ao buscar clientes:", err);
            this.state.update(s => ({
              ...s,
              loading: false,
              error: "Não foi possível carregar os clientes. Tente novamente.",
            }));
            return of(null); // Retorna um Observable seguro para o fluxo não quebrar
          })
        );
      })
    ).subscribe(response => {
      if (response) {
        this.state.set({
          clients: response.content,
          totalElements: response.totalElements,
          loading: false,
          error: null,
        });
      }
    });
  }

  onFilterSubmit(filters: ClientFiltersFormValues): void {
    this.query.update(q => ({
      ...q,
      filters: filters,
      pagination: { ...q.pagination, pageIndex: 0 }
    }));
  }

  onPageChange(event: PageEvent): void {
    this.query.update(q => ({ ...q, pagination: event }));
  }

  forceReload(): void {
    this.query.update(q => ({ ...q, reload: q.reload + 1 }));
  }

  openCreateClientDialog(): void {
    const dialogRef = this.dialog.open(DialogCreateClientComponent, {
      width: '500px',
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.forceReload();
    });
  }

  onUpdateClient(client: Client) {
    const initialData: UpdateClientFormValues = {
      name: client.name,
      email: client.email,
      cnpj: client.cnpj,
      urlToPost: client.urlToPost,
      customerComputex: client.customerComputex ? "S" : "N",
      numberContract: client.numberContract,
      imageActiveDays: client.imageActiveDays
    };
    const data: DialogUpdateClientData = {
      clientId: client.id,
      data: initialData,
    };
    this.openUpdateClientDialog(data);
  }

  openUpdateClientDialog(data: DialogUpdateClientData) {
    const dialogRef = this.dialog.open(DialogUpdateClientComponent, {
      width: '500px',
      data: data
    });

    // Evento de escuta ao fechar a modal
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.forceReload();
    });
  }

  onUpdateComputexPostUrlDialog(client: Client) {
    const data: DialogUpdateComputexPostUrlData = {
      clientId: client.id,
      urlToPost: client.urlToPost,
    };
    this.openUpdateComputexPostUrlDialog(data);
  }

  openUpdateComputexPostUrlDialog(data: DialogUpdateComputexPostUrlData) {
    const dialogRef = this.dialog.open(DialogUpdateComputexPostUrlComponent, {
      width: '500px',
      data: data
    });

    // Evento de escuta ao fechar a modal
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.forceReload();
    });
  }

  onDeleteClient(client: Client): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja desativar o cliente "${client.name}"?`,
      confirmButtonText: 'Excluir'
    };

    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if (confirmed) {
        this.proceedWithDeletion(client.id);
      }
    });
  }

  proceedWithDeletion(clientId: string): void {
    this.clientService.deleteClient(clientId).pipe(
      finalize(() => {
        this.forceReload();
      })
    ).subscribe({
      next: (_) => this.notification.showSuccess("Cliente desativado com sucesso!"),
      error: (_) => this.notification.showError("Ocorreu um erro ao desativar o cliente tente novamente mais tarde")
    });
  }

  onRestoreClient(client: Client): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja restaurar o cliente "${client.name}"?`,
      confirmButtonText: 'Confirmar'
    };

    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if (confirmed) {
        this.proceedWithRestore(client.id);
      }
    });
  }

  proceedWithRestore(clientId: string): void {
    this.clientService.restoreClient(clientId).pipe(
      finalize(() => {
        this.forceReload();
      })
    ).subscribe({
      next: (_) => this.notification.showSuccess("Cliente restaurado com sucesso!"),
      error: (_) => this.notification.showError("Ocorreu um erro ao restaurar o cliente tente novamente mais tarde")
    });
  }

  onRestorePassword(client: Client): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja restaurar a senha do cliente "${client.name}"? Será enviado um e-mail para o cliente definir a nova senha.`,
      confirmButtonText: 'Restaurar'
    };
    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(confirmed) this.proceedWithRestorePassword(client.id);
    });
  }
  
  proceedWithRestorePassword(clientId: string): void {
    this.loader.showLoad("Enviando e-mail...");
    this.clientService.restorePasswordClient(clientId).pipe(
      finalize(() => { this.loader.hideLoad() })
    ).subscribe({
      next: (_) => this.notification.showSuccess("E-mail enviado com sucesso!"),
      error: (err) => this.notification.showError(err.message)
    });
  }
}
