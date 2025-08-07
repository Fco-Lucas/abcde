import { CommonModule } from '@angular/common';
import { Component, computed, Inject, inject, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { AuditLogQuestionService } from '../../../../auditLogQuestions/services/audit-log-question.service';
import { AuditLogQuestionInterface } from '../../../../auditLogQuestions/models/audit-log-questions.model';
import { PageEvent } from '@angular/material/paginator';
import { DialogAuditlogQuestionsFiltersComponent, DialogAuditLogQuestionFiltersFormValues } from '../dialog-auditlog-questions-filters/dialog-auditlog-questions-filters.component';
import { toObservable } from '@angular/core/rxjs-interop';
import { catchError, of, switchMap, tap } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { DialogAuditlogQuestionsListComponent } from '../dialog-auditlog-questions-list/dialog-auditlog-questions-list.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UiErrorComponent } from '../../../../../shared/components/ui-error/ui-error.component';

export interface DialogAuditLogQuestionData {
  imageId: number;
}

interface AuditLogQuestionState {
  entries: AuditLogQuestionInterface[];
  totalElements: number;
  loading: boolean;
  error: string | null;
}

interface AuditLogQuestionQuery {
  filters: Partial<DialogAuditLogQuestionFiltersFormValues>;
  pagination: PageEvent;
  reload: number;
}

@Component({
  selector: 'app-dialog-auditlog-questions',
  imports: [
    CommonModule,
    MatDialogModule,
    MatTableModule,
    MatCardModule,
    DialogAuditlogQuestionsListComponent,
    MatProgressSpinnerModule,
    UiErrorComponent
  ],
  templateUrl: './dialog-auditlog-questions.component.html',
})
export class DialogAuditlogQuestionsComponent {
  private dialogRef = inject(MatDialogRef<DialogAuditlogQuestionsComponent>);
  private auditLogQuestionService = inject(AuditLogQuestionService);

  private query = signal<AuditLogQuestionQuery>({
    filters: {},
    pagination: { pageIndex: 0, pageSize: 10, length: 0 },
    reload: 0,
  });

  private state = signal<AuditLogQuestionState>({
    entries: [],
    totalElements: 0,
    loading: true,
    error: null,
  });

  public readonly entries = computed(() => this.state().entries);
  public readonly totalElements = computed(() => this.state().totalElements);
  public readonly isLoading = computed(() => this.state().loading);
  public readonly error = computed(() => this.state().error);
  public readonly pagination = computed(() => this.query().pagination);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoading() && this.entries().length === 0) return 'loading';
    if (this.error()) return 'error';
    return 'success';
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogAuditLogQuestionData) {
    toObservable(this.query).pipe(
      tap(() => this.state.update(s => ({ ...s, loading: true, error: null }))),
      switchMap(currentQuery => {
        const { pagination, filters } = currentQuery;
        return this.auditLogQuestionService.getAllAuditLogQuestionPageable(
          pagination.pageIndex,
          pagination.pageSize,
          this.data.imageId,
          filters.user,
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
          entries: response.content,
          totalElements: response.totalElements,
          loading: false,
          error: null,
        });
      }
    });
  }

  onFilterSubmit(filters: DialogAuditLogQuestionFiltersFormValues): void {
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
