import { Component, Inject, inject, signal, type OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { ClientUsersService } from '../../../clients/services/client-users.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ClientUserInterface, type UpdateClientUserInterface } from '../../../clients/models/clientUsers.model';
import { finalize } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';

export interface DialogUpdateClientUserInfoFormValues {
  name: string;
  email: string;
}

export interface DataDialogUpdateClientUserInfoInterface {
  clientUser: ClientUserInterface,
}

@Component({
  selector: 'app-dialog-update-user-info',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatFormField,
    MatInputModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    CommonModule
  ],
  templateUrl: './dialog-update-user-info.component.html',
})
export class DialogUpdateUserInfoComponent implements OnInit {
private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateUserInfoComponent>);
  private clientUsersService = inject(ClientUsersService);
  private notification = inject(NotificationService);

  public clientUser!: ClientUserInterface;
  public isLoading = signal(false);
  updateForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: DataDialogUpdateClientUserInfoInterface) {}

  ngOnInit(): void {
    this.clientUser = this.data.clientUser;

    this.updateForm = this.fb.group({
      name: [this.clientUser.name, [Validators.required]],
      email: [this.clientUser.email, [Validators.required, Validators.email]],
    });
  }

  get nameControl() { return this.updateForm.get("name"); }
  get emailControl() { return this.updateForm.get("email"); }

  onSubmit() {
    this.updateForm.markAllAsTouched;
    if(!this.updateForm.valid) {
      console.log(this.updateForm.errors);
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as DialogUpdateClientUserInfoFormValues;
    const data: UpdateClientUserInterface = {
      name: formValues.name,
      email: formValues.email,
    };

    this.clientUsersService.updateClientUser(this.clientUser.clientId, this.clientUser.id, data).pipe(
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
