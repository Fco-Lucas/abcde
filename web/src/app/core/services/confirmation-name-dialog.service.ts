import { inject, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ConfirmationNameDialogComponent, type ConfirmationNameDialogData } from '../../shared/components/confirmation-name-dialog.component/confirmation-name-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class ConfirmationNameDialogService {
  private dialog = inject(MatDialog);

  open(data: ConfirmationNameDialogData): Observable<boolean> {
    const dialogRef = this.dialog.open(ConfirmationNameDialogComponent, {
      data,
      width: '400px',
      disableClose: true,
    });

    return dialogRef.afterClosed();
  }
}
