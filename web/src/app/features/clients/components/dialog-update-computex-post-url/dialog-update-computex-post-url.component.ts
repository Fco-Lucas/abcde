import { Component, Inject, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, type FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import type { DialogUpdateClientComponent } from '../dialog-update-client/dialog-update-client.component';
import { ClientService } from '../../services/client.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { finalize } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

export interface UpdateComputexPostUrlFormValues {
  urlToPost: string;
}

export interface DialogUpdateComputexPostUrlData {
  clientId: string;
  urlToPost: string;  
}

@Component({
  selector: 'app-dialog-update-computex-post-url',
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    ReactiveFormsModule,
    MatIconModule,
    MatTooltipModule
  ],
  templateUrl: './dialog-update-computex-post-url.component.html',
})
export class DialogUpdateComputexPostUrlComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateClientComponent>);
  private clientService = inject(ClientService);  
  private notification = inject(NotificationService);
  updateForm!: FormGroup;

  public clientId!: string;
  public urlToPost!: string;
  public isLoading = signal(false);

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogUpdateComputexPostUrlData) {} 

  ngOnInit(): void {
    this.clientId = this.data.clientId;
    this.urlToPost = this.data.urlToPost;

    this.updateForm = this.fb.group({
      urlToPost: [this.urlToPost, []],
    });
  }

  get urlToPostControl() { return this.updateForm.get('urlToPost'); }

  onSubmit(): void {
    this.updateForm.markAllAsTouched();
    if (!this.updateForm.valid) {
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as UpdateComputexPostUrlFormValues;

    this.clientService.updateClient(this.clientId, formValues).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.updateForm.enable();
      })
    ).subscribe({
      next: () => {
        this.notification.showSuccess("URL atualizada com sucesso!");
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
