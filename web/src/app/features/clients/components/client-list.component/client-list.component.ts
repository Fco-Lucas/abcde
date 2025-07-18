import { Component, inject, OnInit, ViewChild, signal, Input, type OnChanges, type SimpleChanges, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

// Importações do Angular Material
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { ClientService } from '../../services/client.service';
import { Client, ClientStatus } from '../../models/client.model';
import { NgxMaskPipe } from 'ngx-mask';
import { ClientFiltersFormValues } from '../client-filters/client-filters.component';
import { ConfirmationDialogService } from '../../../../core/services/confirmation-dialog.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';
import { DialogUpdateClientComponent, UpdateClientFormValues, type DialogUpdateClientData } from '../dialog-update-client/dialog-update-client.component';
import { MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-client-list',
  imports: [
    CommonModule,
    NgxMaskPipe,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    RouterLink
  ],
  templateUrl: './client-list.component.html',
})
export class ClientListComponent implements OnInit, OnChanges {
  private confirmationDialogService = inject(ConfirmationDialogService);
  private clientService = inject(ClientService);
  private notification = inject(NotificationService);
  private loader = inject(LoadingService);
  readonly dialog = inject(MatDialog);

  // Sinal para controlar o estado de carregamento
  public isLoading = signal(true);

  // Colunas que serão exibidas na tabela. A ordem aqui define a ordem visual.
  public displayedColumns: string[] = ['name', 'cnpj', 'status', ' '];
  
  // Fonte de dados para a tabela
  public dataSource = new MatTableDataSource<Client>();

  public totalElements = 0;
  public pageSize = 10;
  public pageIndex = 0;

  @Output() loadStatusChanged = new EventEmitter<'SUCCESS' | 'ERROR'>();

  @Input() filters: Partial<ClientFiltersFormValues> | null = null;

  // Pega uma referência ao componente paginador do template
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadClientsPage();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filters'] && !changes['filters'].isFirstChange()) {
      // ...reseta o paginador para a primeira página e busca os dados
      if (this.paginator) {
        this.paginator.pageIndex = 0;
      }
      this.pageIndex = 0;
      this.loadClientsPage();
    }
  }

  loadClientsPage(): void {
    this.isLoading.set(true);
    const statusFilter = this.filters?.status === "ALL" ? "" : this.filters?.status as ClientStatus;
    this.clientService.getAllClientsPageable(this.pageIndex, this.pageSize, this.filters?.cnpj, statusFilter).pipe(
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
    this.loadClientsPage();
  }

  handleOpenUpdateClientDialog(client: Client) {
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
      if (result) this.loadClientsPage();
    });
  }

  deleteClient(client: Client): void {
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
    this.loader.showLoad('Excluindo cliente, por favor aguarde...');
    
    this.clientService.deleteClient(clientId).pipe(
      finalize(() => {
        this.loadClientsPage();
        this.loader.hideLoad();
      })
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Cliente desativado com sucesso!");
      },
      error: (_) => {
        this.notification.showError("Ocorreu um erro ao desativar o cliente tente novamente mais tarde");
      }
    });
  }

  restorePassword(client: Client): void {
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
      next: (_) => {
        this.notification.showSuccess("Senha do cliente restaurada para 'abcdefgh' com sucesso!");
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }
}