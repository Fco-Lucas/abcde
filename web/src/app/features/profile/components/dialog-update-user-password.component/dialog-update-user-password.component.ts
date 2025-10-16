import { Component, Inject, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { ClientUsersService } from '../../../clients/services/client-users.service';
import { NotificationService } from '../../../../core/services/notification.service';
import type { UpdateClientUserPasswordInterface } from '../../../clients/models/clientUsers.model';
import { finalize } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';

export interface UpdateClientUserPasswordFormValues {
  currentPassword: string;
  newPassword: string;
  confirmNewPassword: string;
}

export interface DataDialogUpdateClientUserPasswordInterface {
  clientUserId: string;
  clientId: string;
}

@Component({
  selector: 'app-dialog-update-user-password',
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
  templateUrl: './dialog-update-user-password.component.html',
})
export class DialogUpdateUserPasswordComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateUserPasswordComponent>);
  private clientUserService = inject(ClientUsersService);
  private notification = inject(NotificationService);

  clientUserId!: string;
  clientId!: string;
  isLoading = signal(false);

  updateForm = this.fb.group({
    currentPassword: ["", [Validators.required, Validators.minLength(6)]],
    newPassword: ["", [Validators.required, Validators.minLength(6)]],
    confirmNewPassword: ["", [Validators.required, Validators.minLength(6)]],
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: DataDialogUpdateClientUserPasswordInterface) {}

  ngOnInit(): void {
    this.clientUserId = this.data.clientUserId;
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

    const formValues = this.updateForm.getRawValue() as UpdateClientUserPasswordFormValues;

    const data: UpdateClientUserPasswordInterface = {
      newPassword: formValues.newPassword,
      confirmNewPassword: formValues.confirmNewPassword,
    };

    this.clientUserService.updatePasswordClientUser(this.clientId, this.clientUserId, data).pipe(
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
