import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Output, signal, ViewChild, type AfterViewInit, type ElementRef } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import flatpickr from 'flatpickr';
import { Portuguese } from 'flatpickr/dist/l10n/pt';
import moment from 'moment';

interface DateRangeInterface {
  startDate: string;
  endDate: string;
}

export interface DialogAuditLogQuestionFiltersFormValues {
  user: string;
  startDate: string,
  endDate: string,
}

@Component({
  selector: 'app-dialog-auditlog-questions-filters',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatButtonModule
  ],
  templateUrl: './dialog-auditlog-questions-filters.component.html',
})
export class DialogAuditlogQuestionsFiltersComponent implements AfterViewInit {
  private fb = inject(FormBuilder);

  public isLoading = signal<boolean>(false);

  @Output() submitForm = new EventEmitter<DialogAuditLogQuestionFiltersFormValues>();

  @ViewChild('flatpickrInput') flatpickrInput!: ElementRef<HTMLInputElement>;

  filterForm = this.fb.group({
    user: ["", []],
    dateRange: new FormControl<DateRangeInterface | null>(null),
  });

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

    const filters: DialogAuditLogQuestionFiltersFormValues = {
      user: formValues.user as string,
      startDate: formValues.dateRange ? formValues.dateRange.startDate : '',
      endDate: formValues.dateRange ? formValues.dateRange.endDate : '',
    };

    this.submitForm.emit(filters);
  }

  isSubmitButtonDisabled(): boolean {
    return this.filterForm.invalid || this.isLoading();
  }
}
