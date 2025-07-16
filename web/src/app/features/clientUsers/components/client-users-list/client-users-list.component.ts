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
import { ClientUserInterface } from '../../model/clientUsers.model';
import { ClientUsersService } from '../../services/client-users.service';
import { finalize } from 'rxjs';
import { ClientUsersFiltersFormInterface } from '../client-users-filters/client-users-filters.component';

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
  private loader = inject(LoadingService);
  readonly dialog = inject(MatDialog);
  private clientUsersService = inject(ClientUsersService);

  public isLoading = signal(true);

  public displayedColumns: string[] = ['name', 'email', 'permission', 'status', ' '];

  public dataSource = new MatTableDataSource<ClientUserInterface>();

  public totalElements = 0;
  public pageSize = 10;
  public pageIndex = 0;

  @Output() loadStatusChanged = new EventEmitter<'SUCCESS' | 'ERROR'>();

  @Input() clientId: string = "";
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
    // const statusFilter = this.filters?.status === "ALL" ? "" : this.filters?.status as ClientStatus;
    this.clientUsersService.getAllClientsUserPageable(this.clientId, this.pageIndex, this.pageSize).pipe(
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
}
