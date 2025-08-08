import { Component, EventEmitter, inject, Output, signal, ViewChild, type AfterViewInit, type ElementRef } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuditLogAction, AuditLogProgram } from '../../models/audit-log.model';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import flatpickr from 'flatpickr';
import { Portuguese } from 'flatpickr/dist/l10n/pt.js';
import 'flatpickr/dist/flatpickr.min.css';
import moment from 'moment'; // Importar Moment.js diretamente

export interface AuditLogFiltersFormValues {
  action: AuditLogAction | "ALL",
  user: string,
  client: string,
  program: AuditLogProgram | "ALL",
  details: string,
  startDate: string, // Data de início formatada como string (ex: ISO 8601)
  endDate: string,   // Data de fim formatada como string (ex: ISO 8601)
}

export interface DateRangeInterface {
  startDate: string;
  endDate: string;
}

@Component({
  selector: 'app-audit-log-filters',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  templateUrl: './audit-log-filters.component.html'
})
export class AuditLogFiltersComponent implements AfterViewInit {
  private fb = inject(FormBuilder);
  isLoading = signal<boolean>(false);

  filterForm = this.fb.group({
    action: ["ALL", [Validators.required]],
    user: ["", []],
    client: ["", []],
    program: ["ALL", [Validators.required]],
    dateRange: new FormControl<DateRangeInterface | null>(null),
  }); 

  @Output() submitForm = new EventEmitter<AuditLogFiltersFormValues>();

  @ViewChild('flatpickrInput') flatpickrInput!: ElementRef<HTMLInputElement>;

  async ngAfterViewInit() {
    const startOfToday = moment().startOf('day').toDate();
    const endOfToday = moment().endOf('day').toDate();

    await new Promise(resolve => setTimeout(resolve, 100));
    
    flatpickr(this.flatpickrInput.nativeElement, {
      enableTime: true,
      time_24hr: true,
      dateFormat: 'Y-m-d\\TH:i:S',
      locale: Portuguese,
      mode: 'range',
      altInput: true,
      altFormat: 'd/m/Y H:i',
      defaultDate: [startOfToday, endOfToday],
      onChange: (selectedDates, dateStr, instance) => {
        if (selectedDates.length === 2) {
          const startDate = moment(selectedDates[0]).format("YYYY-MM-DDTHH:mm:ss");
          const endDate = moment(selectedDates[1]).format("YYYY-MM-DDTHH:mm:ss");
          this.filterForm.get('dateRange')?.setValue({ startDate, endDate });
        } else {
          this.filterForm.get('dateRange')?.setValue(null);
        }
      },
    });
  }

  onSubmit() {
    if (!this.filterForm.valid) {
      this.filterForm.markAllAsTouched();
      console.error("Formulário inválido:", this.filterForm.errors);
      return;
    }

    const formValues = this.filterForm.value;

    const auditLogFilters: AuditLogFiltersFormValues = {
      action: formValues.action as AuditLogAction | "ALL",
      user: formValues.user as string,
      client: formValues.client as string,
      program: formValues.program as AuditLogProgram | "ALL",
      details: "", // Assuming 'details' is not part of the form, or needs to be added
      startDate: formValues.dateRange ? formValues.dateRange.startDate : '',
      endDate: formValues.dateRange ? formValues.dateRange.endDate : '',
    };

    this.submitForm.emit(auditLogFilters);
  }

  isSubmitButtonDisabled(): boolean {
    return this.filterForm.invalid || this.isLoading();
  }
}