import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function cnpjValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const cnpj = control.value?.replace(/\D/g, ''); // Remove caracteres não numéricos

    if (!cnpj || cnpj.length !== 14 || /^(\d)\1+$/.test(cnpj)) {
      return { cnpjInvalid: true };
    }

    let size = cnpj.length - 2;
    let numbers = cnpj.substring(0, size);
    const digits = cnpj.substring(size);
    let sum = 0;
    let pos = size - 7;

    for (let i = size; i >= 1; i--) {
      sum += numbers.charAt(size - i) * pos--;
      if (pos < 2) {
        pos = 9;
      }
    }

    let result = sum % 11 < 2 ? 0 : 11 - (sum % 11);
    if (result != digits.charAt(0)) {
      return { cnpjInvalid: true };
    }

    size = size + 1;
    numbers = cnpj.substring(0, size);
    sum = 0;
    pos = size - 7;
    for (let i = size; i >= 1; i--) {
      sum += numbers.charAt(size - i) * pos--;
      if (pos < 2) {
        pos = 9;
      }
    }

    result = sum % 11 < 2 ? 0 : 11 - (sum % 11);
    if (result != digits.charAt(1)) {
      return { cnpjInvalid: true };
    }

    return null; // CNPJ válido
  };
}

export function strongPasswordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null; // Não valida se o campo estiver vazio (o 'required' já faz isso)
    }

    const hasUpperCase = /[A-Z]+/.test(value);
    const hasLowerCase = /[a-z]+/.test(value);
    const hasNumeric = /[0-9]+/.test(value);
    const hasSpecial = /[!@#$%^&*(),.?":{}|<>]+/.test(value);

    const passwordValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecial;

    // Retorna um objeto de erro se a senha não for válida, ou null se for válida
    return !passwordValid ? { strong: true } : null;
  };
}

export function passwordsMatchValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const newPassword = control.get('newPassword')?.value;
    const confirmNewPassword = control.get('confirmNewPassword')?.value;

    // Se as senhas não coincidirem, define um erro no campo de confirmação
    if (newPassword && confirmNewPassword && newPassword !== confirmNewPassword) {
      control.get('confirmNewPassword')?.setErrors({ passwordsNotMatching: true });
      return { passwordsNotMatching: true };
    }

    return null;
  };
}