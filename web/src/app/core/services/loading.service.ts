import { inject, Injectable } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LoadingComponent } from '../../shared/components/loading/loading.component';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  private dialog = inject(MatDialog);
  private dialogRef: MatDialogRef<LoadingComponent> | null = null;

  public showLoad(message: string = 'Carregando...'): void {
    // Evita abrir múltiplos loaders
    if (this.dialogRef) {
      return;
    }

    this.dialogRef = this.dialog.open(LoadingComponent, {
      data: { message },
      disableClose: true, // Impede o usuário de fechar o dialog
      panelClass: 'loading-dialog-panel' // Classe para customizar o estilo (opcional)
    });
  }

  public hideLoad(): void {
    if (this.dialogRef) {
      this.dialogRef.close();
      this.dialogRef = null;
    }
  }
}