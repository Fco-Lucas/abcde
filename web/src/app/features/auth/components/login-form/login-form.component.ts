import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { LoginFormValues } from '../../models/login.model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    NgxMaskDirective,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './login-form.html'
})
export class LoginFormComponent {
  // Dispara um evento para a página na qual irá importar o componente
  @Output()
  submitForm = new EventEmitter<LoginFormValues>();

  @Input()
  isLoading: boolean = false;

  loginForm = new FormGroup({
    cpf: new FormControl("", [
      Validators.required,
      Validators.minLength(11),
      Validators.maxLength(11),
      Validators.pattern("^[0-9]*$")
    ]),
    password: new FormControl("", [Validators.required, Validators.minLength(6)])
  });

  onSubmit() {
    this.loginForm.markAllAsTouched();

    if(!this.loginForm.valid) {
      console.error(this.loginForm.errors);
      return;
    }

    // Emite para a página que está importando o componente tipando os valores
    this.submitForm.emit(this.loginForm.value as LoginFormValues);
  }

  get cpfControl() {
    return this.loginForm.get('cpf');
  }

  get passwordControl() {
    return this.loginForm.get('password');
  }

  isSubmitButtonDisabled(): boolean {
    return this.loginForm.invalid || this.isLoading;
  }
  
  // Método para alternar a visibilidade
  hidePassword = signal(true);
  togglePasswordVisibility() {
    this.hidePassword.set(!this.hidePassword());
  }
}
