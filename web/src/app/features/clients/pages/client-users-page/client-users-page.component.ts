import { Component, computed, effect, inject, OnInit, signal, ViewChild } from '@angular/core';
import { ClientUsersListComponent } from '../../components/client-users-list/client-users-list.component';
import { ActivatedRoute } from '@angular/router';
import { PermissionInterface } from '../../../permissions/models/permission.model';
import { PermissionsService } from '../../../permissions/services/permissions.service';
import { ClientUsersFiltersComponent, ClientUsersFiltersFormInterface } from '../../components/client-users-filters/client-users-filters.component';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { DialogCreateClientUserComponent, ClientUsersCreateFormValues, CreateUserDialogData } from '../../components/dialog-create-client-user/dialog-create-client-user.component';
import { MatDialog } from '@angular/material/dialog';
import { ClientService } from '../../../clients/services/client.service';
import { Client } from '../../../clients/models/client.model';
import { ClientUserInterface, ClientUserStatus } from '../../models/clientUsers.model';
import { ClientUsersService } from '../../services/client-users.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ConfirmationDialogService } from '../../../../core/services/confirmation-dialog.service';
import { catchError, combineLatest, finalize, map, of, switchMap, tap } from 'rxjs';
import { DialogUpdateClientUserComponent, DialogUpdateClientUserData, UpdateClientUserFormValues } from '../../components/dialog-update-client-user/dialog-update-client-user.component';
import { PageEvent } from '@angular/material/paginator';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';
import { toObservable } from '@angular/core/rxjs-interop';

interface ClientUserPageState {
  client: Client | null;
  permissions: PermissionInterface[];
  clientUsers: ClientUserInterface[];
  totalElements: number;
  loading: boolean;
  error: string | null;
}

interface ClientUserQuery {
  filters: Partial<ClientUsersFiltersFormInterface>;
  pagination: PageEvent;
  reload: number;
}

@Component({
  selector: 'app-client-users-page',
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    ClientUsersFiltersComponent,
    ClientUsersListComponent,
    MatCardModule,
    MatProgressSpinnerModule,
    UiErrorComponent
  ],
  templateUrl: './client-users-page.component.html',
})
export class ClientUsersPageComponent {
  private route = inject(ActivatedRoute);
  private permissionService = inject(PermissionsService);
  private clientService = inject(ClientService);
  private clientUsersService = inject(ClientUsersService);
  private notification = inject(NotificationService);
  private confirmationDialog = inject(ConfirmationDialogService);
  private dialog = inject(MatDialog);

  private query = signal<ClientUserQuery>({
    filters: {},
    pagination: { pageIndex: 0, pageSize: 10, length: 0 },
    reload: 0,
  });

  private state = signal<ClientUserPageState>({
    client: null,
    permissions: [],
    clientUsers: [],
    totalElements: 0,
    loading: true,
    error: null,
  });

  // Signals computados para a View
  public readonly client = computed(() => this.state().client);
  public readonly permissions = computed(() => this.state().permissions);
  public readonly clientUsers = computed(() => this.state().clientUsers);
  public readonly totalElements = computed(() => this.state().totalElements);
  public readonly isLoading = computed(() => this.state().loading);
  public readonly error = computed(() => this.state().error);
  public readonly pagination = computed(() => this.query().pagination);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoading() && !this.client()) return 'loading';
    if (this.error()) return 'error';
    return 'success';
  });

  constructor() {
    const initialData$ = this.route.paramMap.pipe(
      map(params => params.get('id')),
      switchMap(clientId => {
        if (!clientId) throw new Error("ID do cliente não encontrado na rota.");
        // Busca permissões e cliente em paralelo para otimização
        return combineLatest({
          client: this.clientService.getClientById(clientId),
          permissions: this.permissionService.getPermissions(),
          clientId: of(clientId), // Propaga o clientId para o próximo passo
        });
      })
    );

    const query$ = toObservable(this.query);

    combineLatest([initialData$, query$]).pipe(
      tap(() => this.state.update(s => ({ ...s, loading: true, error: null }))),
      switchMap(([{ clientId, client, permissions }, query]) => {
        // Atualiza o estado com os dados estáticos da página
        this.state.update(s => ({ ...s, client, permissions }));
        
        // Continua o fluxo para buscar a lista paginada de usuários
        const statusFilter = query.filters.status === "ALL" ? "" : query.filters.status as ClientUserStatus;
        return this.clientUsersService.getAllClientsUserPageable(
          clientId, query.pagination.pageIndex, query.pagination.pageSize,
          query.filters.name, query.filters.email, statusFilter
        );
      }),
      catchError(err => {
        this.state.update(s => ({...s, loading: false, error: err.message || "Ocorreu um erro ao carregar a página." }));
        return of(null); // Retorna um Observable seguro para o fluxo não quebrar
      })
    ).subscribe(response => {
      if (response) {
        this.state.update(s => ({
          ...s,
          clientUsers: response.content,
          totalElements: response.totalElements,
          loading: false,
        }));
      }
    });
  }

  onFilterSubmit(filters: ClientUsersFiltersFormInterface): void {
    this.query.update(q => ({...q, filters, pagination: {...q.pagination, pageIndex: 0}}));
  }

  onPageChange(event: PageEvent): void {
    this.query.update(q => ({ ...q, pagination: event }));
  }

  forceReload(): void {
    this.query.update(q => ({ ...q, reload: q.reload + 1 }));
  }

  openCreateClientUserDialog(initialData: ClientUsersCreateFormValues | null = null): void {
    const client = this.client();
    if(!client) return;

    const dialogData = {
      initialData: initialData,
      clientId: client.id,
      clientName: client.name,
      permissions: this.permissions()
    } as CreateUserDialogData;

    const dialogRef = this.dialog.open(DialogCreateClientUserComponent, {
      width: '500px',
      data: dialogData,
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.forceReload();
    });
  }

  openUpdateClientUserDialog(clientUser: ClientUserInterface): void {
    const client = this.client();
    if(!client) return;

    const initialData: UpdateClientUserFormValues = {
      clientId: client.id,
      name: clientUser.name,
      email: clientUser.email,
      permission: clientUser.permission.id
    };

    const dialogData: DialogUpdateClientUserData = {
      initialData: initialData,
      userId: clientUser.id,
      clientId: client.id,
      clientName: client.name,
      permissions: this.permissions()
    };

    const dialogRef = this.dialog.open(DialogUpdateClientUserComponent, {
      width: '500px',
      data: dialogData,
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if(result) this.forceReload();
    });
  }

  deleteClientUser(clientUser: ClientUserInterface): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja desativar o usuário "${clientUser.name}"? Esta ação não pode ser desfeita.`,
      confirmButtonText: 'Excluir'
    };
    this.confirmationDialog.open(dialogData).subscribe(confirmed => {
      if(confirmed) this.proceedWithDeletion(clientUser.clientId, clientUser.id);
    });
  }

  proceedWithDeletion(clientId: string, clientUserId: string): void {
    this.clientUsersService.deleteClientUser(clientId, clientUserId).pipe(
      finalize(() => {
        this.forceReload();
      })
    ).subscribe({
      next: (_) => this.notification.showSuccess("Usuário excluído com sucesso!"),
      error: (err) => this.notification.showError(err.message),
    });
  }

  restorePassword(clientUser: ClientUserInterface): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja restaurar a senha do usuário "${clientUser.name}" para "abcdefgh"? Esta ação não pode ser desfeita.`,
      confirmButtonText: 'Restaurar'
    };
    this.confirmationDialog.open(dialogData).subscribe(confirmed => {
      if(confirmed) this.proceedWithRestorePassword(clientUser.clientId, clientUser.id);
    });
  }

  proceedWithRestorePassword(clientId: string, clientUserId: string): void {
    this.clientUsersService.restorePasswordClientUser(clientId, clientUserId).subscribe({
      next: (_) => this.notification.showSuccess("Senha do usuário restaurada para 'abcdefgh' com sucesso!"),
      error: (err) => this.notification.showError(err.message)
    });
  }
}
