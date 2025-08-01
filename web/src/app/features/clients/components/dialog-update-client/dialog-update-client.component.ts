import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, type OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, type FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { cnpjValidator } from '../../../../shared/utils/custom-validators';
import { NgxMaskDirective } from 'ngx-mask';
import { NotificationService } from '../../../../core/services/notification.service';
import { ClientService } from '../../services/client.service';
import { finalize } from 'rxjs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';

export interface UpdateClientFormValues {
  name: string;
  cnpj: string;
  urlToPost: string;
  imageActiveDays: number;
}

export interface DialogUpdateClientData {
  clientId: string;
  data: UpdateClientFormValues;  
}

@Component({
  selector: 'app-dialog-update-client.component',
  imports: [
    CommonModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogModule,
    NgxMaskDirective,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatIconModule
  ],
  templateUrl: './dialog-update-client.component.html',
})
export class DialogUpdateClientComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateClientComponent>);
  private clientService = inject(ClientService);  
  private notification = inject(NotificationService);
  updateForm!: FormGroup;

  public clientId!: string;
  public initialData!: UpdateClientFormValues;
  public isLoading = signal(false);

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogUpdateClientData) {} 

  ngOnInit(): void {
    this.clientId = this.data.clientId;
    this.initialData = this.data.data;

    this.updateForm = this.fb.group({
      name: [this.initialData.name, [Validators.required]],
      cnpj: [this.initialData.cnpj, [Validators.required, Validators.minLength(18), Validators.maxLength(18)]],
      urlToPost: [this.initialData.urlToPost, []],
      imageActiveDays: [this.initialData.imageActiveDays, [Validators.required]]
    });

    this.updateCnpjValidators(this.cnpjControl?.value);

    this.cnpjControl?.valueChanges.subscribe(value => {
      this.updateCnpjValidators(value);
    });
  }

  updateCnpjValidators(value: string | null): void {
    const onlyNumbers = value?.replace(/\D/g, '') || '';
    
    if (onlyNumbers.length === 14) {
      this.cnpjControl?.setValidators([Validators.required, cnpjValidator()]);
    } else {
      this.cnpjControl?.setValidators([Validators.required, Validators.minLength(18), Validators.maxLength(18)]);
    }
    this.cnpjControl?.updateValueAndValidity({ emitEvent: false });
  }

  get nameControl() { return this.updateForm.get("name"); }
  get cnpjControl() { return this.updateForm.get('cnpj'); }
  get urlToPostControl() { return this.updateForm.get('urlToPost'); }
  get imageActiveDaysControl() { return this.updateForm.get('imageActiveDays'); }

  onSubmit(): void {
    this.updateForm.markAllAsTouched();
    if (!this.updateForm.valid) {
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as UpdateClientFormValues;

    this.clientService.updateClient(this.clientId, formValues).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.updateForm.enable();
      })
    ).subscribe({
      next: () => {
        this.notification.showSuccess("Informações do cliente atualizadas com sucesso!");
        this.dialogRef.close(true);
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  isSubmitButtonDisabled(): boolean {
    return this.updateForm.invalid;
  }
}
