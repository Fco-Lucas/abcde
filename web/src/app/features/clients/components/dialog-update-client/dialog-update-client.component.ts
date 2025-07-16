import { CommonModule } from '@angular/common';
import { Component, Inject, inject, type OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, type FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { cnpjValidator } from '../../../../shared/utils/custom-validators';
import { NgxMaskDirective } from 'ngx-mask';

export interface UpdateClientFormValues {
  name: string;
  cnpj: string;
}

@Component({
  selector: 'app-dialog-update-client.component',
  imports: [
    CommonModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogModule,
    NgxMaskDirective
  ],
  templateUrl: './dialog-update-client.component.html',
})
export class DialogUpdateClientComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateClientComponent>);
  updateForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public initialData: UpdateClientFormValues) {}

  ngOnInit(): void {
    this.updateForm = this.fb.group({
      name: [this.initialData.name, [Validators.required]],
      cnpj: [this.initialData.cnpj, [Validators.required, Validators.minLength(18), Validators.maxLength(18)]],
    });

    this.updateCnpjValidators(this.cnpjControl?.value);

    this.cnpjControl?.valueChanges.subscribe(value => {
      this.updateCnpjValidators(value);
    });
  }

  onSubmit(): void {
    this.updateForm.markAllAsTouched();
    if (!this.updateForm.valid) {
      return;
    }
    this.dialogRef.close(this.updateForm.value as UpdateClientFormValues);
  }

  updateCnpjValidators(value: string | null): void {
    const onlyNumbers = value?.replace(/\D/g, '') || '';
    
    if (onlyNumbers.length === 14) {
      this.cnpjControl?.setValidators([Validators.required, cnpjValidator()]);
    } else {
      this.cnpjControl?.setValidators([Validators.required, Validators.minLength(18), Validators.maxLength(18)]);
    }
    this.cnpjControl?.updateValueAndValidity({ emitEvent: false });
  }

  get nameControl() { return this.updateForm.get("name"); }
  get cnpjControl() { return this.updateForm.get('cnpj'); }

  isSubmitButtonDisabled(): boolean {
    return this.updateForm.invalid;
  }
}
