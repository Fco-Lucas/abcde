import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, ViewChild, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { LotService } from '../../services/lot.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LotCreateInterface, LotInterface, LotStatusEnum, LotTypeEnum } from '../../models/lot.model';
import { finalize } from 'rxjs';
import { MatListModule } from "@angular/material/list";
import { LotImageService } from '../../services/lot-image.service';
import { calculateHash } from '../../../../shared/utils/functions';
import { MatTooltipModule } from '@angular/material/tooltip';

export interface DialogCreateLotData {
  userId: string;
}

export interface CreateLotFormValues {
  type: LotTypeEnum;
  name: string;
  images: FileList | null;
}

type HashedFile = {
  file: File;
  hash: string;
  duplicated: boolean;
};

@Component({
  selector: 'app-dialog-create-lot',
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
  templateUrl: './dialog-create-lot.component.html',
})
export class DialogCreateLotComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogCreateLotComponent>);
  private lotService = inject(LotService);
  private notification = inject(NotificationService);
  private lotImageService = inject(LotImageService);
  
  @ViewChild('inputFile') inputFile!: ElementRef<HTMLInputElement>;

  public userId!: string;
  public isLoading = signal(false);
  public hashedFiles: HashedFile[] = [];

  // Sinal para controlar o texto dinâmico do botão de upload
  public buttonText = signal('Selecionar imagens');

  createForm = this.fb.group({
    type: [LotTypeEnum.ABCDE, [Validators.required]],
    name: ["", [Validators.required]],
    images: [null as FileList | null]
  }); 

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogCreateLotData) {}

  ngOnInit(): void {
    this.userId = this.data.userId;
  }

  get typeControl() { return this.createForm.get("type"); }
  get nameControl() { return this.createForm.get("name"); }
  get imagesControl() { return this.createForm.get("images"); }
  get hasDuplicatedImages(): boolean { return this.hashedFiles.some(f => f.duplicated); }

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
      const isDuplicate = temp.some(f => f.hash === hash) || this.hashedFiles.some(f => f.hash === hash);
      temp.push({ file, hash, duplicated: isDuplicate });
    }

    this.hashedFiles = [...this.hashedFiles, ...temp];
    this.buttonText.set(`${this.hashedFiles.length} imagem(ns) selecionada(s)`);

    // Apenas para manter compatibilidade com o FormGroup
    const uniqueFileList = new DataTransfer();
    this.hashedFiles.forEach(({ file }) => uniqueFileList.items.add(file));
    this.createForm.patchValue({ images: uniqueFileList.files });
  }

   /**
   * Limpa a seleção de arquivos.
   */
  clearFileSelection(): void {
    this.inputFile.nativeElement.value = ''; // Limpa o valor do input
    this.createForm.patchValue({ images: null });
    this.buttonText.set('Selecionar imagens');
    this.hashedFiles = [];
  }

  onSubmit() {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    const uniqueFiles = this.hashedFiles.filter(f => !f.duplicated).map(f => f.file);

    this.isLoading.set(true);
    this.createForm.disable();

    const formValues = this.createForm.getRawValue() as CreateLotFormValues;
    const data: LotCreateInterface = {
      type: formValues.type,
      name: formValues.name,
      userId: this.userId
    };

    this.lotService.createLot(data).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.createForm.enable();
      })
    ).subscribe({
      next: (lot: LotInterface) => {
        // Retorna apenas arquivos únicos
        const files = new DataTransfer();
        uniqueFiles.forEach(file => files.items.add(file));
        this.dialogRef.close({ lot, images: files.files });
      },
      error: (err) => {
        this.notification.showError(err.message || "Ocorreu um erro ao criar o lote.");
      }
    });
  }

  isSubmitButtonDisabled(): boolean {
    return this.createForm.invalid || this.isLoading();
  }
}
