import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, ViewChild, type ElementRef, type OnInit } from '@angular/core';
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
import { calculateHash } from '../../../../../shared/utils/functions';
import { MatTooltipModule } from '@angular/material/tooltip';
import type { LotImageHashInterface } from '../../../models/lot-image.models';

export interface ProcessImagesData {
  hashs: LotImageHashInterface[];
}

export interface ProcessImagesFormValues {
  images: FileList | null;
}

export interface HashedFile {
  file: File;
  hash: string;
  duplicated: boolean; // duplicado localmente (em temp ou hashedFiles)
  alreadyExistsInLot?: boolean; // duplicado no lote vindo do backend
  matricula?: number;
  nomeAluno?: string;
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
    MatListModule,
    MatTooltipModule
  ],
  templateUrl: './dialog-process-images.component.html',
})
export class DialogProcessImagesComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogProcessImagesComponent>);
  private notification = inject(NotificationService);
  private hashs!: LotImageHashInterface[];

  @ViewChild('inputFile') inputFile!: ElementRef<HTMLInputElement>;

  public userId!: string;
  public isLoading = signal(false);
  public hashedFiles: HashedFile[] = [];

  // Sinal para controlar o texto dinâmico do botão de upload
  public buttonText = signal('Selecionar imagens');

  createForm = this.fb.group({
    images: [null as FileList | null]
  }); 

  constructor(@Inject(MAT_DIALOG_DATA) public data: ProcessImagesData) {}

  ngOnInit(): void {
    this.hashs = this.data.hashs;
  }

  get imagesControl() { return this.createForm.get("images"); }
  get hasDuplicatedImages(): boolean { return this.hashedFiles.some(f => f.duplicated || f.alreadyExistsInLot); }

  async onFileSelected(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) {
      this.hashedFiles = [];
      this.createForm.patchValue({ images: null });
      this.buttonText.set('Selecionar imagens');
      return;
    }

    const fileArray = Array.from(input.files);
    const temp: HashedFile[] = [];

    for (const file of fileArray) {
      const hash = await calculateHash(file);

      // Verifica se já existe no lote (via backend)
      const matchInLot = this.hashs.find(h => h.hash === hash);
      const alreadyExistsInLot = !!matchInLot;

      // Verifica duplicação local (na lista temporária ou já existente)
      const duplicatedLocal = temp.some(f => f.hash === hash) || this.hashedFiles.some(f => f.hash === hash);

      const hashedFile: HashedFile = {
        file,
        hash,
        duplicated: duplicatedLocal,
        alreadyExistsInLot,
        matricula: matchInLot?.matricula,
        nomeAluno: matchInLot?.nomeAluno,
      };

      temp.push(hashedFile);
    }

    this.hashedFiles = [...this.hashedFiles, ...temp];
    this.buttonText.set(`${this.hashedFiles.length} imagem(ns) selecionada(s)`);

    // Apenas imagens únicas (não duplicadas nem existentes no lote)
    const filesToSend = this.hashedFiles
      .filter(f => !f.duplicated && !f.alreadyExistsInLot)
      .map(f => f.file);

    const dt = new DataTransfer();
    filesToSend.forEach(file => dt.items.add(file));
    this.createForm.patchValue({ images: dt.files });
  }

  onSubmit() {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    const filesToSend = this.hashedFiles
      .filter(f => !f.duplicated && !f.alreadyExistsInLot)
      .map(f => f.file);

    if (filesToSend.length === 0) {
      this.notification.showError('Nenhuma imagem válida para envio!');
      return;
    }

    const dt = new DataTransfer();
    filesToSend.forEach(file => dt.items.add(file));

    this.dialogRef.close({ images: dt.files });
  }

  clearFileSelection(): void {
    this.inputFile.nativeElement.value = ''; // Limpa o valor do input
    this.createForm.patchValue({ images: null });
    this.buttonText.set('Selecionar imagens');
    this.hashedFiles = [];
  }

  isSubmitButtonDisabled(): boolean {
    return this.createForm.invalid || this.isLoading();
  }
}
