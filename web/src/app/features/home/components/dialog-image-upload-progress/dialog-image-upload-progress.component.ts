import { Component, Inject, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { forkJoin, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { LotImageService } from '../../services/lot-image.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { MatListModule } from '@angular/material/list';

export interface DialogImageUploadProgressData {
  lotId: number;
  images: FileList;
}

@Component({
  selector: 'app-dialog-image-upload-progress',
  standalone: true,
  imports: [
    CommonModule, MatDialogModule, MatProgressBarModule,
    MatIconModule, MatButtonModule, MatListModule
  ],
  templateUrl: './dialog-image-upload-progress.component.html',
  styleUrl: './dialog-image-upload-progress.style.scss'
})
export class DialogImageUploadProgressComponent implements OnInit {
  public data: DialogImageUploadProgressData = inject(MAT_DIALOG_DATA);
  private dialogRef = inject(MatDialogRef<DialogImageUploadProgressComponent>);
  private lotImageService = inject(LotImageService);
  private notification = inject(NotificationService);

  totalImages = signal(0);
  processedImages = signal(0);
  isDone = signal(false);
  hasError = signal(false);
  failedUploads = signal<string[]>([]);

  progressPercentage = computed(() => this.totalImages() > 0 ? (this.processedImages() / this.totalImages()) * 100 : 0);
  title = computed(() => this.hasError() ? 'Erro no Upload' : (this.isDone() ? 'Concluído' : 'Enviando Imagens'));
  statusMessage = computed(() => `Processando ${this.processedImages()} de ${this.totalImages()} imagem(ns)...`);

  ngOnInit(): void {
    if (!this.data.images || this.data.images.length === 0) {
      this.dialogRef.close();
      return;
    }
    this.totalImages.set(this.data.images.length);
    this.startUploads();
  }

  // startUploads(): void {
  //   const uploadObservables = Array.from(this.data.images).map(file => {
  //     const formData = new FormData();
  //     formData.append('file', file);
      
  //     return this.lotImageService.processImage(this.data.lotId, formData).pipe(
  //       tap(() => this.processedImages.update(count => count + 1)),
  //       catchError(error => {
  //         this.hasError.set(true);
  //         console.error('Erro ao enviar imagem:', error);
  //         this.processedImages.update(count => count + 1); // Ainda conta como processado para a barra de progresso avançar
  //         return of(null);
  //       })
  //     );
  //   });

  //   forkJoin(uploadObservables).subscribe(() => {
  //     this.isDone.set(true);
  //     if (!this.hasError()) {
  //       this.notification.showSuccess('Lote e imagens processados com sucesso!');
  //       this.dialogRef.close();
  //     } else {
  //       this.notification.showWarning('Lote criado, mas algumas imagens falharam ao enviar.');
  //     }
  //   });
  // }

  startUploads(): void {
    const uploadObservables = Array.from(this.data.images).map(file => {
      const formData = new FormData();
      formData.append('file', file);
      
      return this.lotImageService.processImage(this.data.lotId, formData).pipe(
        tap(() => this.processedImages.update(count => count + 1)),
        catchError(error => {
          // 2. Lógica de erro atualizada
          this.hasError.set(true);
          // Adiciona o nome do arquivo à lista de falhas
          this.failedUploads.update(list => [...list, file.name]); 
          console.error(`Erro ao enviar a imagem '${file.name}':`, error);
          // Ainda conta como processado para a barra de progresso avançar
          this.processedImages.update(count => count + 1); 
          // Retorna um observable de sucesso (com valor nulo) para não cancelar o forkJoin
          return of(null); 
        })
      );
    });

    forkJoin(uploadObservables).subscribe(() => {
      this.isDone.set(true);
      // Notificações exibidas apenas no final de todo o processo
      if (!this.hasError()) {
        this.notification.showSuccess('Todas as imagens foram enviadas com sucesso!');
        // Fecha o dialog automaticamente apenas em caso de sucesso total
        setTimeout(() => this.dialogRef.close(true), 1000);
      } else {
        this.notification.showWarning('Algumas imagens falharam ao enviar, certifique-se de cada imagem possuir todas as bordas com a cor preta visíveis e nítidas');
      }
    });
  }
}
