import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { RegisterFormValues } from '../../models/register.model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';

@Component({
  selector: 'app-register-form',
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    NgxMaskDirective,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './register-form.component.html',
})
export class RegisterFormComponent {
  @Output()
  submitForm = new EventEmitter<RegisterFormValues>();
  
  @Input()
  isLoading: boolean = false;

  registerForm = new FormGroup({
    cnpj: new FormControl("", [Validators.required, Validators.minLength(14), Validators.maxLength(14)]),
    password: new FormControl("", [Validators.required, Validators.minLength(6)])
  });

  onSubmit() {
    this.registerForm.markAllAsTouched;

    if(!this.registerForm.valid) {
      console.error(this.registerForm.errors);
      return;
    }

    this.submitForm.emit(this.registerForm.value as RegisterFormValues);
  }

  get cnpjControl() {
    return this.registerForm.get('cnpj');
  }

  get passwordControl() {
    return this.registerForm.get('password');
  }

  isSubmitButtonDisabled(): boolean {
    return this.registerForm.invalid || this.isLoading;
  }
  
  // Método para alternar a visibilidade
  hidePassword = signal(true);
  togglePasswordVisibility() {
    this.hidePassword.set(!this.hidePassword());
  }
}
