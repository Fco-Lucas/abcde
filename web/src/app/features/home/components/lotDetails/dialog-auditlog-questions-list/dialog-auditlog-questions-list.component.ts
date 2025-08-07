import { Component, EventEmitter, Input, Output, type OnChanges, type SimpleChanges } from '@angular/core';
import type { AuditLogQuestionInterface } from '../../../../auditLogQuestions/models/audit-log-questions.model';
import { MatPaginatorModule, type PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import moment from 'moment';

@Component({
  selector: 'app-dialog-auditlog-questions-list',
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatTableModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './dialog-auditlog-questions-list.component.html',
})
export class DialogAuditlogQuestionsListComponent implements OnChanges {
  @Input() entries: AuditLogQuestionInterface[] = [];
  @Input() isLoading: boolean = false;
  @Input() totalElements: number = 0;
  @Input() pageSize: number = 10;
  @Input() pageIndex: number = 0;

  @Output() pageChange = new EventEmitter<PageEvent>();

  public displayedColumns: string[] = ['date', 'client', 'user', 'details'];
  public dataSource = new MatTableDataSource<AuditLogQuestionInterface>();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['entries']) {
      this.dataSource.data = this.entries ?? [];
    }
  }

  formatToBr(datetime: string | null): string {
    if (!datetime) return '-';
    return moment(datetime).format('DD/MM/YYYY HH:mm');
  }
}
