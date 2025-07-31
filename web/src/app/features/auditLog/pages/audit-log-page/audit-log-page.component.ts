import { CommonModule } from '@angular/common';
import { Component, inject, signal, computed, effect } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PageEvent } from '@angular/material/paginator';

import { AuditLogFiltersComponent, AuditLogFiltersFormValues } from '../../components/audit-log-filters/audit-log-filters.component';
import { AuditLogListComponent } from '../../components/audit-log-list/audit-log-list.component';
import { AuditLogService } from '../../services/audit-log.service';
import { AuditLogInterface } from '../../models/audit-log.model';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';

interface AuditLogState {
  data: AuditLogInterface[];
  totalElements: number;
  loading: boolean;
  error: string | null;
}

@Component({
  selector: 'app-audit-log-page',
  imports: [
    CommonModule,
    AuditLogFiltersComponent,
    AuditLogListComponent,
    MatCardModule,
    MatProgressSpinnerModule,
    UiErrorComponent
  ],
  templateUrl: './audit-log-page.component.html',
})
export class AuditLogPageComponent {
  private auditLogService = inject(AuditLogService);

  public filters = signal<Partial<AuditLogFiltersFormValues>>({});
  public pagination = signal({ pageIndex: 0, pageSize: 10 });

  private state = signal<AuditLogState>({
    data: [],
    totalElements: 0,
    loading: true, // Inicia como true para a carga inicial
    error: null,
  });
  
  public data = computed(() => this.state().data);
  public totalElements = computed(() => this.state().totalElements);
  public isLoading = computed(() => this.state().loading);
  public error = computed(() => this.state().error);

  constructor() {
    // Sempre que 'filters' ou 'pagination' mudar, este código executa
    effect(() => {
      const currentFilters = this.filters();
      const currentPage = this.pagination();
      this.loadAuditLog(currentFilters, currentPage);
    });
  }

  private loadAuditLog(filters: Partial<AuditLogFiltersFormValues>, page: { pageIndex: number, pageSize: number }): void {
    this.state.update(s => ({ ...s, loading: true, error: null })); // Inicia o loading

    this.auditLogService.getAllAuditLogPageable(
      page.pageIndex,
      page.pageSize,
      filters.action === "ALL" ? "" : filters.action,
      filters.user,
      filters.program === "ALL" ? "" : filters.program,
      filters.details,
      filters.startDate,
      filters.endDate
    ).subscribe({
      next: (response) => {
        this.state.set({
          data: response.content,
          totalElements: response.totalElements,
          loading: false,
          error: null
        });
      },
      error: (err) => {
        console.error("Erro ao buscar registros:", err);
        this.state.set({
          data: [],
          totalElements: 0,
          loading: false,
          error: "Falha ao carregar os dados. Tente novamente mais tarde."
        });
      }
    });
  }

  onFilterSubmit(filters: AuditLogFiltersFormValues): void {
    this.pagination.set({ ...this.pagination(), pageIndex: 0 });
    this.filters.set(filters);
  }

  onPageChange(event: PageEvent): void {
    this.pagination.set({ pageIndex: event.pageIndex, pageSize: event.pageSize });
  }
}