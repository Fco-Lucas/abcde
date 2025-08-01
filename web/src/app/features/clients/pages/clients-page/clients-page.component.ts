import { Component, computed, effect, inject, signal } from '@angular/core';

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
import { finalize } from 'rxjs';

interface ClientsPageState {
  clients: Client[];
  totalElements: number;
  loading: boolean;
  error: string | null;
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
  private clientService = inject(ClientService);
  private confirmationDialogService = inject(ConfirmationDialogService);
  private notification = inject(NotificationService);
  private dialog = inject(MatDialog);

  public filters = signal<Partial<ClientFiltersFormValues>>({});
  public pagination = signal<PageEvent>({ pageIndex: 0, pageSize: 10, length: 0 });
  public reloadTrigger = signal(0);

  private state = signal<ClientsPageState>({
    clients: [],
    totalElements: 0,
    loading: true,
    error: null,
  });

  public clients = computed(() => this.state().clients);
  public totalElements = computed(() => this.state().totalElements);
  public isLoading = computed(() => this.state().loading);
  public error = computed(() => this.state().error);

  public viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.state().loading && this.clients().length === 0) return 'loading';
    if (this.state().error) return 'error';
    return 'success';
  });

  constructor() {
    // Quando os filtros, paginação ou reloadTrigger mudarem, ele chamará a lista de clientes passando-os
    effect(() => {
      const filters = this.filters();
      const page = this.pagination();
      this.reloadTrigger();

      this.loadClientsPage(page, filters);
    });
  }

  loadClientsPage(page: PageEvent, filters: Partial<ClientFiltersFormValues>): void {
    this.state.update(s => ({ ...s, loading: true, error: null }));
    const statusFilter = filters.status === "ALL" ? "" : filters.status as ClientStatus;
    this.clientService.getAllClientsPageable(page.pageIndex, page.pageSize, filters.cnpj, statusFilter)
      .subscribe({
        next: (response) => this.state.set({
          clients: response.content,
          totalElements: response.totalElements,
          loading: false,
          error: null,
        }),
        error: (err) => this.state.set({
          clients: [],
          totalElements: 0,
          loading: false,
          error: "Não foi possível carregar os clientes. Tente novamente.",
        }),
      });
  }

  onFilterSubmit(filters: ClientFiltersFormValues): void {
    this.pagination.update(p => ({ ...p, pageIndex: 0 })); // Reseta a página
    this.filters.set(filters);
  }

  onPageChange(event: PageEvent): void {
    this.pagination.set(event);
  }

  openCreateClientDialog(): void {
    const dialogRef = this.dialog.open(DialogCreateClientComponent, {
      width: '500px',
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.reloadTrigger.update(v => v + 1);
    });
  }

  onUpdateClient(client: Client) {
    const initialData: UpdateClientFormValues = {
      name: client.name,
      cnpj: client.cnpj
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
      if (result) this.reloadTrigger.update(v => v + 1);
    });
  }

  onDeleteClient(client: Client): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja desativar o cliente "${client.name}"? Esta ação não pode ser desfeita.`,
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
        this.reloadTrigger.update(v => v + 1);
      })
    ).subscribe({
      next: (_) => this.notification.showSuccess("Cliente desativado com sucesso!"),
      error: (_) => this.notification.showError("Ocorreu um erro ao desativar o cliente tente novamente mais tarde")
    });
  }

  onRestorePassword(client: Client): void {
    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja restaurar a senha do cliente "${client.name}" para "abcdefgh"? Esta ação não pode ser desfeita.`,
      confirmButtonText: 'Restaurar'
    };
    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(confirmed) this.proceedWithRestorePassword(client.id);
    });
  }
  
  proceedWithRestorePassword(clientId: string): void {
    this.clientService.restorePasswordClient(clientId).subscribe({
      next: (_) => this.notification.showSuccess("Senha do cliente restaurada para 'abcdefgh' com sucesso!"),
      error: (err) => this.notification.showError(err.message)
    });
  }
}
