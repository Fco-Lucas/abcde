import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import type { PermissionInterface } from '../../../permissions/models/permission.model';

export type ClientUsersStatusTypeFiltersForm = "ALL" | "ACTIVE" | "INACTIVE";

export interface ClientUsersFiltersFormInterface {
  name: string;
  email: string;
  permission: string;
  status: ClientUsersStatusTypeFiltersForm
}

@Component({
  selector: 'app-client-users-filters',
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './client-users-filters.component.html',
})
export class ClientUsersFiltersComponent {
  private fb = inject(FormBuilder);

  filterForm = this.fb.group({
    name: ["", []],
    email: ["", []],
    permission: ["ALL", [Validators.required]],
    status: ["ALL", [Validators.required]]
  });

  @Input() isLoading: boolean = false;
  @Input() permissions: PermissionInterface[] = [];

  @Output() submitForm = new EventEmitter<ClientUsersFiltersFormInterface>();

  onSubmit() {
    this.filterForm.markAllAsTouched;

    if(!this.filterForm.valid) {
      console.error(this.filterForm.errors);
      return;
    }

    this.submitForm.emit(this.filterForm.value as ClientUsersFiltersFormInterface);
  }

  isSubmitButtonDisabled(): boolean {
    return this.filterForm.invalid || this.isLoading;
  }

  get nameControl() {
    return this.filterForm.get("name");
  }

  get emailControl() {
    return this.filterForm.get("email");
  }

  get permissionControl() {
    return this.filterForm.get("permission");
  }

  get statusControl() {
    return this.filterForm.get("status");
  }
}
