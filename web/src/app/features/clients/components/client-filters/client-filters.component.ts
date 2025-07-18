import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';
import { MatIconModule } from '@angular/material/icon';

export type ClientStatusFilterType = 'ALL' | 'ACTIVE' | 'INACTIVE';

export interface ClientFiltersFormValues {
  cnpj: string;
  status: ClientStatusFilterType
}

@Component({
  selector: 'app-client-filters',
  imports: [
    CommonModule,
    NgxMaskDirective,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  templateUrl: './client-filters.component.html',
})
export class ClientFiltersComponent {
  filterForm = new FormGroup({
    cnpj: new FormControl("", []),
    status: new FormControl("ACTIVE", [Validators.required])
  });

  @Output()
  submitForm = new EventEmitter<ClientFiltersFormValues>();

  @Input()
  isLoading: boolean = false;

  onSubmit() {
    this.filterForm.markAllAsTouched;

    if(!this.filterForm.valid) {
      console.error(this.filterForm.errors);
      return;
    }

    this.submitForm.emit(this.filterForm.value as ClientFiltersFormValues);
  }

  isSubmitButtonDisabled(): boolean {
    return this.filterForm.invalid || this.isLoading;
  }

  get cnpjControl() {
    return this.filterForm.get("cnpj");
  }

  get statusControl() {
    return this.filterForm.get("status");
  }
}
