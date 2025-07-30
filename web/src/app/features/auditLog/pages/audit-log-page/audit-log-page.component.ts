import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { AuditLogFiltersComponent, type AuditLogFiltersFormValues } from '../../components/audit-log-filters/audit-log-filters.component';
import { AuditLogListComponent } from '../../components/audit-log-list/audit-log-list.component';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-audit-log-page',
  imports: [
    CommonModule,
    AuditLogFiltersComponent,
    AuditLogListComponent,
    MatCardModule
  ],
  templateUrl: './audit-log-page.component.html',
})
export class AuditLogPageComponent {
  public currentFilters = signal<Partial<AuditLogFiltersFormValues> | null>(null);

  onFilterSubmit(filters: AuditLogFiltersFormValues): void {
    console.log(filters);
    this.currentFilters.set(filters);
  }
}
