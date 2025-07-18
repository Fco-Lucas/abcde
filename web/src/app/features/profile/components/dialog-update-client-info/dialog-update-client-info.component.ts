import { Component, Inject, inject, signal } from '@angular/core';
import { Client, UpdateClientInterface } from '../../../clients/models/client.model';
import { FormBuilder, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { DialogUpdateClientUserComponent } from '../../../clients/components/dialog-update-client-user/dialog-update-client-user.component';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { NotificationService } from '../../../../core/services/notification.service';
import { ClientService } from '../../../clients/services/client.service';
import { finalize } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NgxMaskDirective } from 'ngx-mask';
import { cnpjValidator } from '../../../../shared/utils/custom-validators';
import { CommonModule } from '@angular/common';

export interface DialogUpdateClientInfoFormValues {
  name: string;
  cnpj: string;
}

export interface DataDialogUpdateClientInfoInterface {
  client: Client,
}

@Component({
  selector: 'app-dialog-update-client-info',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatFormField,
    MatInputModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    NgxMaskDirective,
    CommonModule
  ],
  templateUrl: './dialog-update-client-info.component.html',
})
export class DialogUpdateClientInfoComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateClientUserComponent>);
  private clientService = inject(ClientService);
  private notification = inject(NotificationService);

  public client!: Client;
  public isLoading = signal(false);
  updateForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: DataDialogUpdateClientInfoInterface) {}

  ngOnInit(): void {
    this.client = this.data.client;

    this.updateForm = this.fb.group({
      name: [this.client.name, [Validators.required]],
      cnpj: [this.client.cnpj, [Validators.required, Validators.minLength(18), Validators.maxLength(18)]],
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
  get cnpjControl() { return this.updateForm.get("cnpj"); }

  onSubmit() {
    this.updateForm.markAllAsTouched;
    if(!this.updateForm.valid) {
      console.log(this.updateForm.errors);
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as DialogUpdateClientInfoFormValues;
    const data: UpdateClientInterface = {
      name: formValues.name,
      cnpj: formValues.cnpj,
    };

    this.clientService.updateClient(this.client.id, data).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.updateForm.enable();
      })
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Informações do cliente atualizadas com sucesso!");
        this.dialogRef.close(true);
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  isSubmitButtonDisabled(): boolean {
    return this.updateForm.invalid || this.isLoading();
  }
}
