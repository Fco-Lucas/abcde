import { Component, EventEmitter, Inject, Output, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, type AbstractControl, type FormGroup, type ValidationErrors, type ValidatorFn } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

export interface ConfirmationNameDialogData {
  title: string;
  message: string;
  expectedName: string;
  confirmButtonText?: string;
  cancelButtonText?: string;
}

export interface ConfirmationNameDialogFormValues {
  name: string;
}

@Component({
  selector: 'app-confirmation-name-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  templateUrl: './confirmation-name-dialog.component.html',
})
export class ConfirmationNameDialogComponent {
  private fb = inject(FormBuilder);
  private isLoading = signal<boolean>(false);
  filterForm!: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<ConfirmationNameDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmationNameDialogData
  ) {
    this.filterForm = this.fb.group({
      name: ['', [Validators.required]]
    }, {
      validators: [this.nameMatchValidator()]
    });
  }

  private nameMatchValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const enteredName = control.get('name')?.value;
      const expectedName = this.data.expectedName.trim();

      if (enteredName?.trim() === expectedName) {
        return null;
      }
      
      return { nameMismatch: true };
    };
  }

  // O resto do seu código já está correto e não precisa de alterações
  @Output() submitForm = new EventEmitter<ConfirmationNameDialogFormValues>();

  onSubmit() {
    this.filterForm.markAllAsTouched();
    if (this.filterForm.invalid) {
      return;
    }
    this.submitForm.emit(this.filterForm.value as ConfirmationNameDialogFormValues);
    this.dialogRef.close(true);
  }

  get nameControl() { return this.filterForm.get("name"); }

  isSubmitButtonDisabled(): boolean {
    return this.filterForm.invalid || this.isLoading();
  }
}
