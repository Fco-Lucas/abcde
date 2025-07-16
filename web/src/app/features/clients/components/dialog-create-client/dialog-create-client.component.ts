import { ChangeDetectionStrategy, Component, Inject, inject, type OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

// Importações do Material e ngx-mask
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { NgxMaskDirective } from 'ngx-mask';
import { cnpjValidator } from '../../../../shared/utils/custom-validators';

// --- Interface para os dados do formulário ---
export interface CreateFormValues {
  name: string;
  cnpj: string;
  password: string;
}

@Component({
  selector: 'app-dialog-create-client',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NgxMaskDirective,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
  ],
  templateUrl: './dialog-create-client.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogCreateClientComponent implements OnInit {
  // --- Injeção de Dependências ---
  private fb = inject(FormBuilder);
  readonly dialogRef = inject(MatDialogRef<DialogCreateClientComponent>);

  constructor(@Inject(MAT_DIALOG_DATA) public initialData: CreateFormValues | null) {}

  // --- Formulário Reativo ---
  createForm = this.fb.group({
    name: ['', [Validators.required]],
    cnpj: ['', [Validators.required, Validators.minLength(18), Validators.maxLength(18)]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  // --- Lógica do Componente ---
  onSubmit(): void {
    this.createForm.markAllAsTouched();
    if (!this.createForm.valid) {
      return;
    }
    this.dialogRef.close(this.createForm.value as CreateFormValues);
  }

  ngOnInit(): void {
    if (this.initialData) {
      this.createForm.patchValue(this.initialData);
    }
    
    this.cnpjControl?.valueChanges.subscribe(value => {
      this.updateCnpjValidators(value);
    });
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

  // --- Getters e UI Helpers ---
  get nameControl() { return this.createForm.get("name"); }
  get cnpjControl() { return this.createForm.get('cnpj'); }
  get passwordControl() { return this.createForm.get('password'); }

  isSubmitButtonDisabled(): boolean {
    return this.createForm.invalid;
  }

  hidePassword = signal(true);
  togglePasswordVisibility() {
    this.hidePassword.set(!this.hidePassword());
  }
}