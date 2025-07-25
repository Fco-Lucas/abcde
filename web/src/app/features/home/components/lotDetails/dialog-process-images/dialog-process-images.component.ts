import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, ViewChild, type ElementRef } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, type FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { LotImageService } from '../../../services/lot-image.service';
import { NotificationService } from '../../../../../core/services/notification.service';

export interface ProcessImagesFormValues {
  images: FileList | null;
}

@Component({
  selector: 'app-dialog-process-images',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatListModule
  ],
  templateUrl: './dialog-process-images.component.html',
})
export class DialogProcessImagesComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogProcessImagesComponent>);
  private notification = inject(NotificationService);

  @ViewChild('inputFile') inputFile!: ElementRef<HTMLInputElement>;

  public userId!: string;
  public isLoading = signal(false);

  // Sinal para controlar o texto dinâmico do botão de upload
  public buttonText = signal('Selecionar gabaritos');

  createForm = this.fb.group({
    images: [null as FileList | null]
  }); 

  constructor(@Inject(MAT_DIALOG_DATA) public data: ProcessImagesFormValues) {}

  get imagesControl() { return this.createForm.get("images"); }

  /**
   * Chamado quando o usuário seleciona arquivos no input.
   * @param event O evento de mudança do input.
   */
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const files = input.files;
      // Atualiza o valor do form control com a FileList
      this.createForm.patchValue({ images: files });
      // Atualiza o texto do botão
      this.buttonText.set(`${files.length} gabarito(s) selecionado(s)`);
    } else {
      // Caso o usuário cancele a seleção, reseta
      this.createForm.patchValue({ images: null });
      this.buttonText.set('Selecionar gabaritos');
    }
  }

  onSubmit() {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    const formValues = this.createForm.getRawValue() as ProcessImagesFormValues;
    if(!formValues.images || formValues.images.length === 0) {
      this.notification.showError('Selcione ao menos 1 gabarito para continuar');
      return;
    };

    console.log(formValues.images);

    this.dialogRef.close({ images: formValues.images });
  }

   /**
   * Limpa a seleção de arquivos.
   */
  clearFileSelection(): void {
    this.inputFile.nativeElement.value = ''; // Limpa o valor do input
    this.createForm.patchValue({ images: null });
    this.buttonText.set('Selecionar gabaritos');
  }

  isSubmitButtonDisabled(): boolean {
    return this.createForm.invalid || this.isLoading();
  }
}
