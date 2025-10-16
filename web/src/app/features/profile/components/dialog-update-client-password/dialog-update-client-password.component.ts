import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, type OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ClientService } from '../../../clients/services/client.service';
import { NotificationService } from '../../../../core/services/notification.service';
import type { UpdateClientPasswordInterface } from '../../../clients/models/client.model';
import { finalize } from 'rxjs';
import { MatIconModule } from '@angular/material/icon';

export interface UpdateClientPasswordFormValues {
  currentPassword: string;
  newPassword: string;
  confirmNewPassword: string;
}

export interface DataDialogUpdateClientPasswordInterface {
  clientId: string;
}

@Component({
  selector: 'app-dialog-update-client-password',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormField,
    MatInputModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    CommonModule,
    MatIconModule
  ],
  templateUrl: './dialog-update-client-password.component.html',
})
export class DialogUpdateClientPasswordComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateClientPasswordComponent>);
  private clientService = inject(ClientService);
  private notification = inject(NotificationService);

  clientId!: string;
  isLoading = signal(false);

  updateForm = this.fb.group({
    currentPassword: ["", [Validators.required, Validators.minLength(6)]],
    newPassword: ["", [Validators.required, Validators.minLength(6)]],
    confirmNewPassword: ["", [Validators.required, Validators.minLength(6)]],
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: DataDialogUpdateClientPasswordInterface) {}

  ngOnInit(): void {
    this.clientId = this.data.clientId;
  }

  onSubmit(): void {
    this.updateForm.markAllAsTouched;
    if(!this.updateForm.valid) {
      console.error(this.updateForm.errors);
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as UpdateClientPasswordFormValues;

    const data: UpdateClientPasswordInterface = {
      newPassword: formValues.newPassword,
      confirmNewPassword: formValues.confirmNewPassword,
    };

    this.clientService.updatePasswordClient(this.clientId, data).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.updateForm.enable();
      })
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Senha alterada com sucesso!");
        this.dialogRef.close(true);
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    })
  }

  get currentPasswordControl() { return this.updateForm.get("currentPassword") }
  get newPasswordControl() { return this.updateForm.get("newPassword") }
  get confirmNewPasswordControl() { return this.updateForm.get("confirmNewPassword") }

  hideCurrentPassword = signal(true);
  toggleCurrentPasswordVisibility() {
    this.hideCurrentPassword.set(!this.hideCurrentPassword());
  }
  hideNewPassword = signal(true);
  toggleNewPasswordVisibility() {
    this.hideNewPassword.set(!this.hideNewPassword());
  }
  hideConfirmNewPassword = signal(true);
  toggleConfirmNewPasswordVisibility() {
    this.hideConfirmNewPassword.set(!this.hideConfirmNewPassword());
  }

  isSubmitButtonDisabled(): boolean {
    return this.updateForm.invalid || this.isLoading();
  }
}
