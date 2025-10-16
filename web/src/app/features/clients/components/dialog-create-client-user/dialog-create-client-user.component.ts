import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { PermissionInterface } from '../../../permissions/models/permission.model';
import { MatSelectModule } from '@angular/material/select';
import { NotificationService } from '../../../../core/services/notification.service';
import { ClientUsersService } from '../../services/client-users.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import type { CreateClientUserInterface } from '../../models/clientUsers.model';
import { finalize } from 'rxjs';

export interface ClientUsersCreateFormValues {
  clientId: string,
  name: string,
  email: string,
  // password: string,
  permission: string,
}

export interface CreateUserDialogData {
  initialData: ClientUsersCreateFormValues | null;
  clientId: string;
  clientName: string;
  permissions: PermissionInterface[]; // Use o tipo correto aqui
}

@Component({
  selector: 'app-dialog-create-client-user',
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
  templateUrl: './dialog-create-client-user.component.html',
})
export class DialogCreateClientUserComponent implements OnInit {
  private fb = inject(FormBuilder);
  readonly dialogRef = inject(MatDialogRef<DialogCreateClientUserComponent>);
  private clientUserService = inject(ClientUsersService);
  private notification = inject(NotificationService);

  public clientId: string = "";
  public clientName: string = "";
  public permissions: PermissionInterface[] = [];
  public isLoading = signal(false);
  createForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: CreateUserDialogData | null) {}

  ngOnInit(): void {
    if (this.data?.clientId) this.clientId = this.data.clientId;
    if (this.data?.clientName) this.clientName = this.data.clientName;
    if (this.data?.permissions) this.permissions = this.data.permissions;
    if (this.data?.initialData) this.createForm.patchValue(this.data.initialData);

    this.createForm = this.fb.group({
      clientId: [{ value: this.clientId, disabled: true }, [Validators.required, Validators.minLength(36), Validators.maxLength(36)]],
      name: ["", [Validators.required]],
      email: ["", [Validators.required, Validators.email]],
      // password: ["", [Validators.required, Validators.minLength(6)]],
      permission: [this.permissions[0].id, [Validators.required]],
    });
  }

  get clientIdControl() { return this.createForm.get("clientId"); }
  get nameControl() { return this.createForm.get("name"); }
  get emailControl() { return this.createForm.get('email'); }
  // get passwordControl() { return this.createForm.get('password'); }
  get permissionControl() { return this.createForm.get('permission'); }
  
  hidePassword = signal(true);
  togglePasswordVisibility() {
    this.hidePassword.set(!this.hidePassword());
  }

  onSubmit() {
    this.createForm.markAllAsTouched;
    if(!this.createForm.valid) {
      console.error(this.createForm.errors);
      return;
    }

    this.isLoading.set(true);
    this.createForm.disable();

    const formValues = this.createForm.getRawValue() as ClientUsersCreateFormValues;
    const data: CreateClientUserInterface = {
      clientId: formValues.clientId,
      name: formValues.name,
      email: formValues.email,
      // password: formValues.password,
      permission: Number(formValues.permission),
    }

    this.clientUserService.createClientUser(this.clientId, data).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.createForm.enable();
      })
    ).subscribe({
      next: () => {
        this.notification.showSuccess("Usuário criado com sucesso!");
        this.dialogRef.close(true);
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  isSubmitButtonDisabled(): boolean {
    return this.createForm.invalid || this.isLoading();
  }
}
