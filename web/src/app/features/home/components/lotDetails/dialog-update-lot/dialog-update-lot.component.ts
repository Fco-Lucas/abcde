import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, type OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, type FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LotService } from '../../../services/lot.service';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import type { LotUpdateInterface } from '../../../models/lot.model';
import { finalize } from 'rxjs';
import { NotificationService } from '../../../../../core/services/notification.service';

export interface DialogUpdateLotData {
  lotId: number;
  lotName: string;
}

export interface UpdateLotFormValues {
  name: string;
}

@Component({
  selector: 'app-dialog-update-lot',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './dialog-update-lot.component.html',
})
export class DialogUpdateLotComponent implements OnInit {
  private fb = inject(FormBuilder);
  private lotService = inject(LotService);
  private notification = inject(NotificationService);
  private dialogRef = inject(MatDialogRef<DialogUpdateLotComponent>);

  public isLoading = signal<boolean>(false);
  updateForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogUpdateLotData) {}

  get nameControl() { return this.updateForm.get("name"); }

  ngOnInit(): void {
    this.updateForm = this.fb.group({
      name: [this.data.lotName, [Validators.required]],
    }); 
  }

  onSubmit() {
    if(!this.updateForm.valid) {
      this.updateForm.markAllAsTouched();
      console.error(this.updateForm.errors);
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as UpdateLotFormValues;
    const data: LotUpdateInterface = {
      name: formValues.name,
    };

    this.lotService.updateLot(this.data.lotId, data).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.updateForm.enable();
      })
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Informações do lote atualizadas com sucesso!");
        this.dialogRef.close(data);
      },
      error: (err) => {
        this.notification.showError(err.message || "Ocorreu um erro ao criar o lote.");
      }
    });
  }
  
  isSubmitButtonDisabled(): boolean {
    return this.updateForm.invalid || this.isLoading();
  }
}
