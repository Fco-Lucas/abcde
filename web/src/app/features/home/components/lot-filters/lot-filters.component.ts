import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LotStatusEnum } from '../../models/lot.model';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

export interface LotFiltersFormValues {
  name: string,
  status: LotStatusEnum | "ALL"
}

@Component({
  selector: 'app-lot-filters',
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatButtonModule
  ],
  templateUrl: './lot-filters.component.html',
})
export class LotFiltersComponent {
  private fb = inject(FormBuilder);

  filterForm = this.fb.group({
    name: ["", []],
    status: ["ALL", [Validators.required]]
  });

  @Output()
  submitForm = new EventEmitter<LotFiltersFormValues>();

  @Input()
  isLoading: boolean = false;

  onSubmit() {
    this.filterForm.markAllAsTouched;

    if(!this.filterForm.valid) {
      console.error(this.filterForm.errors);
      return;
    }

    this.submitForm.emit(this.filterForm.value as LotFiltersFormValues);
  }

  isSubmitButtonDisabled(): boolean {
    return this.filterForm.invalid || this.isLoading;
  }

  get nameControl() {
    return this.filterForm.get("name");
  }

  get statusControl() {
    return this.filterForm.get("status");
  }
}
