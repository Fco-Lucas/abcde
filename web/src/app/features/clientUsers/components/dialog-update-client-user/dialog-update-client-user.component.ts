import { Component, Inject, inject, signal } from '@angular/core';
import { ClientUsersService } from '../../services/client-users.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { PermissionInterface } from '../../../permissions/models/permission.model';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { finalize } from 'rxjs';
import { UpdateClientUserInterface } from '../../model/clientUsers.model';

export interface UpdateClientUserFormValues {
  clientId: string;
  name: string;
  email: string;
  permission: number;
};

export interface DialogUpdateClientUserData {
  initialData: UpdateClientUserFormValues;
  userId: string;
  clientId: string;
  clientName: string;
  permissions: PermissionInterface[];
};

@Component({
  selector: 'app-dialog-update-client-user',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './dialog-update-client-user.component.html',
})
export class DialogUpdateClientUserComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateClientUserComponent>);
  private clientUserService = inject(ClientUsersService);
  private notification = inject(NotificationService);

  public userId!: string;
  public clientId!: string;
  public clientName!: string;
  public permissions!: PermissionInterface[];
  public isLoading = signal(false);
  updateForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogUpdateClientUserData) {}

  ngOnInit(): void {
    this.userId = this.data.userId;
    this.clientId = this.data.clientId;
    this.clientName = this.data.clientName;
    this.permissions = this.data.permissions;

    this.updateForm = this.fb.group({
      clientId: [{ value: this.clientId, disabled: true }, [Validators.required]],
      name: [this.data.initialData.name, [Validators.required]],
      email: [this.data.initialData.email, [Validators.required, Validators.email]],
      permission: [this.data.initialData.permission, [Validators.required]],
    });
  }

  get clientIdControl() { return this.updateForm.get("clientId"); }
  get nameControl() { return this.updateForm.get("name"); }
  get emailControl() { return this.updateForm.get("email"); }
  get permissionControl() { return this.updateForm.get("permission"); }

  onSubmit() {
    this.updateForm.markAllAsTouched;
    if(!this.updateForm.valid) {
      console.log(this.updateForm.errors);
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as UpdateClientUserFormValues;
    const data: UpdateClientUserInterface = {
      clientId: this.clientId,
      name: formValues.name,
      email: formValues.email,
      permission: Number(formValues.permission)
    };

    this.clientUserService.updateClientUser(this.userId, data).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.updateForm.enable();
      })
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Informações do usuário atualizadas com sucesso!");
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
