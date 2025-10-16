import { Component, EventEmitter, Input, Output, signal, type OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { NgxMaskDirective } from 'ngx-mask';
import { LoginFormValues } from '../../models/login.model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { cnpjValidator } from '../../../../shared/utils/custom-validators';

@Component({
  selector: 'app-login-form',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NgxMaskDirective,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './login-form.component.html'
})
export class LoginFormComponent implements OnInit {
  loginForm = new FormGroup({
    login: new FormControl("", [Validators.required]),
    password: new FormControl("", [Validators.required, Validators.minLength(6)])
  });
  
  // Dispara um evento para a página na qual irá importar o componente
  @Output()
  submitForm = new EventEmitter<LoginFormValues>();

  @Input()
  isLoading: boolean = false;

  ngOnInit(): void {
    // Escuta as mudanças no valor do campo 'login'
    this.loginControl?.valueChanges.subscribe(value => {
      this.updateLoginValidators(value);
    });
  }

  updateLoginValidators(value: string | null): void {
    const onlyNumbers = value?.replace(/\D/g, '') || '';
    
    // Remove validadores antigos para evitar conflitos
    this.loginControl?.clearValidators();

    if (onlyNumbers.length === 14) {
      // Se parece um CNPJ, adiciona os validadores de required e de CNPJ
      this.loginControl?.setValidators([Validators.required, cnpjValidator()]);
    } else {
      // Senão, adiciona os validadores de required e de e-mail
      this.loginControl?.setValidators([Validators.required, Validators.email]);
    }

    // Atualiza o estado de validação do controle sem emitir um novo evento de valueChanges
    this.loginControl?.updateValueAndValidity({ emitEvent: false });
  }

  onSubmit() {
    this.loginForm.markAllAsTouched();

    if(!this.loginForm.valid) {
      console.error(this.loginForm.errors);
      return;
    }

    // Emite para a página que está importando o componente tipando os valores
    this.submitForm.emit(this.loginForm.value as LoginFormValues);
  }

  get loginControl() {
    return this.loginForm.get('login');
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

  public getLoginMask(): string {
    const value = this.loginControl?.value || '';

    // Remove caracteres não numéricos para a verificação
    const onlyNumbers = value.replace(/\D/g, '');

    // Se tiver 14 dígitos, aplica a máscara de CNPJ.
    if (onlyNumbers.length === 14) return '00.000.000/0000-00';
    
    // Senão, não aplica nenhuma máscara (permitindo e-mail, etc).
    return '';
  }
}
