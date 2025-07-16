import { inject, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ConfirmationDialogComponent, ConfirmationDialogData } from '../../shared/components/confirmation-dialog/confirmation-dialog.component';


@Injectable({
  providedIn: 'root'
})
export class ConfirmationDialogService {
  private dialog = inject(MatDialog);

  /**
   * Abre um dialog de confirmação.
   * @param data Os dados para exibir no dialog (título, mensagem, etc.).
   * @returns Um Observable que emite `true` se o usuário confirmar, ou `false`/`undefined` se cancelar.
   */
  open(data: ConfirmationDialogData): Observable<boolean> {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data,
      width: '400px',
      disableClose: true, // Impede que o usuário feche clicando fora
    });

    return dialogRef.afterClosed();
  }
}