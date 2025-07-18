import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output, signal, ViewChild, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ConfirmationDialogService } from '../../../../core/services/confirmation-dialog.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';
import { MatDialog } from '@angular/material/dialog';
import { ClientUserInterface, type ClientUserStatus } from '../../models/clientUsers.model';
import { ClientUsersService } from '../../services/client-users.service';
import { finalize } from 'rxjs';
import { ClientUsersFiltersFormInterface } from '../client-users-filters/client-users-filters.component';
import type { PermissionInterface } from '../../../permissions/models/permission.model';
import { DialogUpdateClientUserComponent, type DialogUpdateClientUserData, type UpdateClientUserFormValues } from '../dialog-update-client-user/dialog-update-client-user.component';

@Component({
  selector: 'app-client-users-list',
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule
  ],
  templateUrl: './client-users-list.component.html',
})
export class ClientUsersListComponent implements OnInit, OnChanges {
  private confirmationDialogService = inject(ConfirmationDialogService);
  private notification = inject(NotificationService);
  readonly dialog = inject(MatDialog);
  private clientUsersService = inject(ClientUsersService);

  public isLoading = signal(true);

  public displayedColumns: string[] = ['name', 'email', 'permission', 'status', ' '];

  public dataSource = new MatTableDataSource<ClientUserInterface>();

  public totalElements = 0;
  public pageSize = 10;
  public pageIndex = 0;

  @Output() loadStatusChanged = new EventEmitter<'SUCCESS' | 'ERROR'>();

  @Input() clientId!: string;
  @Input() clientName!: string;
  @Input() permissions!: PermissionInterface[];
  @Input() filters: Partial<ClientUsersFiltersFormInterface> | null = null;

  // Pega uma referência ao componente paginador do template
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadClientUsersPage();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filters'] && !changes['filters'].isFirstChange()) {
      // ...reseta o paginador para a primeira página e busca os dados
      if (this.paginator) {
        this.paginator.pageIndex = 0;
      }
      this.pageIndex = 0;
      this.loadClientUsersPage();
    }
  }

  loadClientUsersPage(): void {
    this.isLoading.set(true);
    const statusFilter = this.filters?.status === "ALL" ? "" : this.filters?.status as ClientUserStatus;
    this.clientUsersService.getAllClientsUserPageable(this.clientId, this.pageIndex, this.pageSize, this.filters?.name, this.filters?.email, statusFilter).pipe(
      // O finalize garante que o isLoading será false ao final, seja sucesso ou erro.
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: (response) => {
        // Alimenta a fonte de dados da tabela com os clientes recebidos
        this.dataSource.data = response.content;
        this.totalElements = response.totalElements;
        this.loadStatusChanged.emit('SUCCESS');
      },
      error: (error) => {
        console.error("Erro ao buscar clientes:", error);
        this.loadStatusChanged.emit('ERROR');
      }
    });
  }

  handlePageEvent(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadClientUsersPage();
  }

  openUpdateClientUserDialog(clientUser: ClientUserInterface): void {
    const initialData: UpdateClientUserFormValues = {
      clientId: this.clientId,
      name: clientUser.name,
      email: clientUser.email,
      permission: clientUser.permission.id
    };

    const dialogData: DialogUpdateClientUserData = {
      initialData: initialData,
      userId: clientUser.id,
      clientId: this.clientId,
      clientName: this.clientName,
      permissions: this.permissions
    };

    const dialogRef = this.dialog.open(DialogUpdateClientUserComponent, {
      width: '500px',
      data: dialogData,
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.loadClientUsersPage();
    });
  }

  deleteClientUser(clientUser: ClientUserInterface): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja desativar o usuário "${clientUser.name}"? Esta ação não pode ser desfeita.`,
      confirmButtonText: 'Excluir'
    };
    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(confirmed) this.proceedWithDeletion(clientUser.clientId, clientUser.id);
    });
  }

  proceedWithDeletion(clientId: string, clientUserId: string): void {
    this.clientUsersService.deleteClientUser(clientId, clientUserId).pipe(
      finalize(() => {
        this.loadClientUsersPage();
      })
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Usuário excluído com sucesso!");
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  restorePassword(clientUser: ClientUserInterface): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja restaurar a senha do usuário "${clientUser.name}" para "abcdefgh"? Esta ação não pode ser desfeita.`,
      confirmButtonText: 'Restaurar'
    };
    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(confirmed) this.proceedWithRestorePassword(clientUser.clientId, clientUser.id);
    });
  }

  proceedWithRestorePassword(clientId: string, clientUserId: string): void {
    this.clientUsersService.restorePasswordClientUser(clientId, clientUserId).subscribe({
      next: (_) => {
        this.notification.showSuccess("Senha do usuário restaurada para 'abcdefgh' com sucesso!");
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }
}
