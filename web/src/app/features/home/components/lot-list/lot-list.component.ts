import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output, signal, ViewChild, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { LotService } from '../../services/lot.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { finalize } from 'rxjs';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { LotInterface } from '../../models/lot.model';
import { LotFiltersFormValues } from '../lot-filters/lot-filters.component';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { LotStatusBadgeComponent } from '../lot-status-badge/lot-status-badge.component';
import { LotStateService } from '../../services/lot-state.service';
import { Router } from '@angular/router';
import type { PermissionInterface } from '../../../permissions/models/permission.model';

@Component({
  selector: 'app-lot-list',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinner,
    MatPaginatorModule,
    LotStatusBadgeComponent
  ],
  templateUrl: './lot-list.component.html',
})
export class LotListComponent implements OnInit, OnChanges {
  // Injeção de dependências
  private logStateService = inject(LotStateService);
  private lotService = inject(LotService);
  private notification = inject(NotificationService);
  private router = inject(Router);
  private dialog = inject(MatDialog);

  // Sinais para controle de estado
  public isLoading = signal(true);
  public lots = signal<LotInterface[]>([]); // Sinal para armazenar os lotes

  // Propriedades da paginação
  public totalElements = 0;
  public pageSize = 10;
  public pageIndex = 0;

  // Inputs e Outputs
  @Input() filters: Partial<LotFiltersFormValues> | null = null;
  @Input() userPermissions!: PermissionInterface;
  @Output() loadStatusChanged = new EventEmitter<'SUCCESS' | 'ERROR'>();

  // Referência ao paginador no template
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadLotsPage();
  }

  ngOnChanges(changes: SimpleChanges): void {
    // Se os filtros mudarem, recarrega os dados na primeira página
    if (changes['filters'] && !changes['filters'].isFirstChange()) {
      if (this.paginator) {
        this.paginator.pageIndex = 0;
      }
      this.pageIndex = 0;
      this.loadLotsPage();
    }
  }

  loadLotsPage(): void {
    this.isLoading.set(true);
    this.lots.set([]); // Limpa os dados antigos para evitar mostrar dados incorretos durante o carregamento

    const filterStatus = this.filters?.status === "ALL" ? "" : this.filters?.status;
    this.lotService.getAllLotsUserPageable(
      this.pageIndex, 
      this.pageSize, 
      this.filters?.name, 
      this.filters?.client, 
      this.filters?.clientUser, 
      filterStatus
    ).pipe(
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: (response) => {
        this.lots.set(response.content); // Armazena os lotes no sinal
        this.totalElements = response.totalElements;
        this.loadStatusChanged.emit('SUCCESS');
      },
      error: (error) => {
        console.error("Erro ao buscar lotes:", error);
        this.notification.showError("Ocorreu um erro ao buscar os lotes. Tente novamente.");
        this.loadStatusChanged.emit('ERROR');
      }
    });
  }

  handlePageEvent(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadLotsPage();
  }

  // Exemplo de uma ação em um card
  viewLotDetails(lot: LotInterface): void {
    this.logStateService.selectLot(lot);
    this.logStateService.setPermissions(this.userPermissions);
    this.router.navigate(["/app/loteDetails"]);
  }
}
