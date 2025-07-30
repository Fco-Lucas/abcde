import { CommonModule } from '@angular/common';
import { Component, inject, Input, signal, ViewChild, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { AuditLogAction, AuditLogInterface, AuditLogProgram } from '../../models/audit-log.model';
import { AuditLogFiltersFormValues } from '../audit-log-filters/audit-log-filters.component';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { AuditLogService } from '../../services/audit-log.service';
import { finalize } from 'rxjs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import moment from 'moment';


@Component({
  selector: 'app-audit-log-list',
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatTableModule
  ],
  templateUrl: './audit-log-list.component.html',
  styleUrl: './audit-log-list.component.scss'
})
export class AuditLogListComponent implements OnInit, OnChanges {
  formatToBr(datetime: string | null): string {
    if (!datetime) return '-';
    return moment(datetime).format('DD/MM/YYYY HH:mm');
  }

  formatActionToBr(action: AuditLogAction): string {
    switch(action) {
      case "CREATE":
        return "Criação";
      case "UPDATE":
        return "Atualização";
      case "DELETE":
        return "Exclusão";
      case "LOGIN":
        return "Login";
      case "PROCESSED":
        return "Upload de imagens";
      default:
        return '-';
    }
  }
  
  formatProgramToBr(program: AuditLogProgram): string {
    switch(program) {
      case "CLIENT":
        return "Clientes";
      case "CLIENT_USER":
        return "Usuário do clientes";
      case "LOT":
        return "Lotes";
      case "LOT_IMAGE":
        return "Gabaritos";
      case "AUTH":
        return "Autenticação";
      default:
        return '-';
    }
  }

  private auditLogService = inject(AuditLogService);

  public displayedColumns: string[] = ['createdAt', 'user', 'action', 'program', 'details'];

  public isLoading = signal(true);
  
  // Fonte de dados para a tabela
  public dataSource = new MatTableDataSource<AuditLogInterface>();

  public totalElements = 0;
  public pageSize = 10;
  public pageIndex = 0;

  @Input() filters: Partial<AuditLogFiltersFormValues> | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadEntriesList();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filters'] && !changes['filters'].isFirstChange()) {
      if (this.paginator) {
        this.paginator.pageIndex = 0;
      }
      this.pageIndex = 0;
      this.loadEntriesList();
    }
  }

  loadEntriesList(): void {
    this.isLoading.set(true);
    
    const actionFilter = this.filters?.action === "ALL" ? "" : this.filters?.action as AuditLogAction;
    const programFilter = this.filters?.program === "ALL" ? "" : this.filters?.program as AuditLogProgram;

    this.auditLogService.getAllAuditLogPageable(
      this.pageIndex, 
      this.pageSize,
      actionFilter,
      this.filters?.user,
      programFilter,
      this.filters?.details,
      this.filters?.startDate, 
      this.filters?.endDate, 
    ).pipe(
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: (response) => {
        // Alimenta a fonte de dados da tabela com os clientes recebidos
        this.dataSource.data = response.content;
        this.totalElements = response.totalElements;
      },
      error: (error) => {
        console.error("Erro ao buscar registros:", error);
      }
    });
  }

  handlePageEvent(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadEntriesList();
  }
}
