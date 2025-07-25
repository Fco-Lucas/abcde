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
import { LotCreateInterface, LotInterface } from '../../models/lot.model';
import { finalize } from 'rxjs';
import { MatListModule } from "@angular/material/list";
import { LotImageService } from '../../services/lot-image.service';

export interface DialogCreateLotData {
  userId: string;
}

export interface CreateLotFormValues {
  name: string;
  images: FileList | null;
}

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
    MatListModule
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

  // Sinal para controlar o texto dinâmico do botão de upload
  public buttonText = signal('Selecionar imagens');

  createForm = this.fb.group({
    name: ["", [Validators.required]],
    images: [null as FileList | null]
  }); 

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogCreateLotData) {}

  ngOnInit(): void {
    this.userId = this.data.userId;
  }

  get nameControl() { return this.createForm.get("name"); }
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
      this.buttonText.set(`${files.length} imagem(ns) selecionada(s)`);
    } else {
      // Caso o usuário cancele a seleção, reseta
      this.createForm.patchValue({ images: null });
      this.buttonText.set('Selecionar imagens');
    }
  }

   /**
   * Limpa a seleção de arquivos.
   */
  clearFileSelection(): void {
    this.inputFile.nativeElement.value = ''; // Limpa o valor do input
    this.createForm.patchValue({ images: null });
    this.buttonText.set('Selecionar imagens');
  }

  onSubmit() {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.createForm.disable();

    const formValues = this.createForm.getRawValue() as CreateLotFormValues;
    const data: LotCreateInterface = {
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
        // 2. Retorna o lote criado e a lista de imagens para o orquestrador.
        this.dialogRef.close({ lot, images: formValues.images });
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
