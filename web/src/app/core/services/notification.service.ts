import { inject, Injectable } from '@angular/core';
import {
  MatSnackBar,
  MatSnackBarConfig,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private snackBar = inject(MatSnackBar);

  // Configuração padrão que pode ser usada por todos os snackbars
  private defaultConfig(
    vPosition: MatSnackBarVerticalPosition, 
    hPosition: MatSnackBarHorizontalPosition, 
    duration: number
  ): MatSnackBarConfig {
    return {
      verticalPosition: vPosition,
      horizontalPosition: hPosition,
      duration: duration,
    };
  }

  /**
   * Exibe uma notificação de sucesso (fundo verde).
   * @param message A mensagem a ser exibida.
   * @param duration Duração em milissegundos (padrão: 3000ms).
   * @param vPosition Posição vertical (padrão: 'top').
   * @param hPosition Posição horizontal (padrão: 'center').
   */
  showSuccess(
    message: string,
    duration = 3000,
    vPosition: MatSnackBarVerticalPosition = 'top',
    hPosition: MatSnackBarHorizontalPosition = 'center'
  ): void {
    const config = this.defaultConfig(vPosition, hPosition, duration);
    config.panelClass = ['success-snackbar']; // Classe CSS customizada
    this.snackBar.open(message, 'Fechar', config);
  }

  /**
   * Exibe uma notificação de erro (fundo vermelho).
   * @param message A mensagem a ser exibida.
   * @param duration Duração em milissegundos (padrão: 5000ms).
   * @param vPosition Posição vertical (padrão: 'top').
   * @param hPosition Posição horizontal (padrão: 'center').
   */
  showError(
    message: string,
    duration = 5000,
    vPosition: MatSnackBarVerticalPosition = 'top',
    hPosition: MatSnackBarHorizontalPosition = 'center'
  ): void {
    const config = this.defaultConfig(vPosition, hPosition, duration);
    config.panelClass = ['error-snackbar']; // Classe CSS customizada
    this.snackBar.open(message, 'Fechar', config);
  }

  /**
   * Exibe uma notificação de aviso (fundo laranja).
   * @param message A mensagem a ser exibida.
   * @param duration Duração em milissegundos (padrão: 4000ms).
   * @param vPosition Posição vertical (padrão: 'top').
   * @param hPosition Posição horizontal (padrão: 'center').
   */
  showWarning(
    message: string,
    duration = 4000,
    vPosition: MatSnackBarVerticalPosition = 'top',
    hPosition: MatSnackBarHorizontalPosition = 'center'
  ): void {
    const config = this.defaultConfig(vPosition, hPosition, duration);
    config.panelClass = ['warning-snackbar']; // Classe CSS customizada
    this.snackBar.open(message, 'Fechar', config);
  }
}