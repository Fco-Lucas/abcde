import { Component, EventEmitter, Input, Output, signal, type OnInit } from '@angular/core';
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
import { cnpjValidator } from '../../../../shared/utils/custom-validators';

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
export class RegisterFormComponent implements OnInit {
  registerForm = new FormGroup({
    name: new FormControl("", [Validators.required]),
    cnpj: new FormControl("", [Validators.required, Validators.minLength(14), Validators.maxLength(14)]),
    password: new FormControl("", [Validators.required, Validators.minLength(6)])
  });

  @Output()
  submitForm = new EventEmitter<RegisterFormValues>();
  
  @Input()
  isLoading: boolean = false;

  onSubmit() {
    this.registerForm.markAllAsTouched;

    if(!this.registerForm.valid) {
      console.error(this.registerForm.errors);
      return;
    }

    this.submitForm.emit(this.registerForm.value as RegisterFormValues);
  }

  ngOnInit(): void {
    this.cnpjControl?.valueChanges.subscribe(value => {
      this.updateCnpjValidators(value);
    });
  }

  updateCnpjValidators(value: string | null): void {
    const onlyNumbers = value?.replace(/\D/g, '') || '';
    
    // Remove validadores antigos para evitar conflitos
    this.cnpjControl?.clearValidators();

    if (onlyNumbers.length === 14) this.cnpjControl?.setValidators([Validators.required, cnpjValidator()]);

    // Atualiza o estado de validação do controle sem emitir um novo evento de valueChanges
    this.cnpjControl?.updateValueAndValidity({ emitEvent: false });
  }

  get nameControl() {
    return this.registerForm.get("name");
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
