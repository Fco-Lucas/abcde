import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { passwordsMatchValidator, strongPasswordValidator } from '../../../../shared/utils/custom-validators';

export interface DefinePasswordFormValues {
  newPassword: string;
  confirmNewPassword: string;
}

@Component({
  selector: 'app-define-password-form',
  imports: [CommonModule, MatCardModule, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatIconModule, MatProgressSpinnerModule, MatButtonModule],
  templateUrl: './define-password-form.component.html',
})
export class DefinePasswordFormComponent {
private fb = inject(FormBuilder);

  @Output() private submitForm = new EventEmitter<DefinePasswordFormValues>();
  @Input() public isLoading: boolean = false;

  public definePasswordForm = this.fb.group({
    newPassword: ["", [Validators.required, Validators.minLength(6), strongPasswordValidator()
    ]],
    confirmNewPassword: ["", [Validators.required]]
  }, { 
    validators: passwordsMatchValidator() // Validador aplicado ao grupo para comparar os campos
  });

  onSubmit() {
    if (!this.definePasswordForm.valid) {
      this.definePasswordForm.markAllAsTouched();
      return;
    }
    const formValues = this.definePasswordForm.getRawValue() as DefinePasswordFormValues;
    this.submitForm.emit(formValues);
  }

  // Método para controlar o botão de submit
  isSubmitButtonDisabled(): boolean {
    return this.definePasswordForm.invalid || this.isLoading;
  }
  
  // Métodos para alternar a visibilidade dos inputs
  public hideNewPassword = signal(true);
  toggleNewPasswordVisibility() { this.hideNewPassword.set(!this.hideNewPassword()); }
  public hideConfirmNewPassword = signal(true);
  toggleConfirmNewPasswordVisibility() { this.hideConfirmNewPassword.set(!this.hideConfirmNewPassword()); }

  get newPasswordControl() { return this.definePasswordForm.get('newPassword'); }
  get confirmNewPasswordControl() { return this.definePasswordForm.get('confirmNewPassword'); }
}
