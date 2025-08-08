import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageEvent } from '@angular/material/paginator';

// ✅ Imports do RxJS e Interop
import { toObservable } from '@angular/core/rxjs-interop';
import { switchMap, tap, catchError, of } from 'rxjs';

// Models, Services e Components
import { AuditLogFiltersComponent, AuditLogFiltersFormValues } from '../../components/audit-log-filters/audit-log-filters.component';
import { AuditLogListComponent } from '../../components/audit-log-list/audit-log-list.component';
import { AuditLogService } from '../../services/audit-log.service';
import { AuditLogInterface } from '../../models/audit-log.model';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';

import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

interface AuditLogState {
  data: AuditLogInterface[];
  totalElements: number;
  loading: boolean;
  error: string | null;
}

interface AuditLogQuery {
  filters: Partial<AuditLogFiltersFormValues>;
  pagination: PageEvent;
  reload: number;
}

@Component({
  selector: 'app-audit-log-page',
  standalone: true,
  imports: [
    CommonModule, AuditLogFiltersComponent, AuditLogListComponent, MatCardModule,
    MatProgressSpinnerModule, UiErrorComponent
  ],
  templateUrl: './audit-log-page.component.html',
})
export class AuditLogPageComponent {
  private auditLogService = inject(AuditLogService);

  private query = signal<AuditLogQuery>({
    filters: {},
    pagination: { pageIndex: 0, pageSize: 10, length: 0 },
    reload: 0,
  });

  private state = signal<AuditLogState>({
    data: [],
    totalElements: 0,
    loading: true,
    error: null,
  });

  public readonly data = computed(() => this.state().data);
  public readonly totalElements = computed(() => this.state().totalElements);
  public readonly isLoading = computed(() => this.state().loading);
  public readonly error = computed(() => this.state().error);
  public readonly pagination = computed(() => this.query().pagination);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoading() && this.data().length === 0) return 'loading';
    if (this.error()) return 'error';
    return 'success';
  });

  constructor() {
    toObservable(this.query).pipe(
      tap(() => this.state.update(s => ({ ...s, loading: true, error: null }))),
      switchMap(currentQuery => {
        const { pagination, filters } = currentQuery;
        return this.auditLogService.getAllAuditLogPageable(
          pagination.pageIndex,
          pagination.pageSize,
          filters.action === "ALL" ? "" : filters.action,
          filters.client,
          filters.user,
          filters.program === "ALL" ? "" : filters.program,
          filters.details,
          filters.startDate,
          filters.endDate
        ).pipe(
          catchError(err => {
            console.error("Erro ao buscar registros:", err);
            this.state.update(s => ({...s, loading: false, error: "Falha ao carregar os dados." }));
            return of(null);
          })
        );
      })
    ).subscribe(response => {
      if (response) {
        this.state.set({
          data: response.content,
          totalElements: response.totalElements,
          loading: false,
          error: null,
        });
      }
    });
  }

  onFilterSubmit(filters: AuditLogFiltersFormValues): void {
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
}