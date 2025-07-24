import { CommonModule } from '@angular/common';
import { Component, Inject, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

export interface LotImagesFiltersData {
  student: string;
}

export interface LotImagesFiltersFormValues {
  student: string;
}

@Component({
  selector: 'app-dialog-filter-images',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './dialog-filter-images.component.html',
})
export class DialogFilterImagesComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogFilterImagesComponent>);

  public currentStudent!: string;
  public filterForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: LotImagesFiltersData) {}

  ngOnInit(): void {
    this.currentStudent = this.data.student;
    
    this.filterForm = this.fb.group({
      student: [this.currentStudent, []]
    });
  }

  isLoading: boolean = false;

  onSubmit() {
    this.filterForm.markAllAsTouched();

    if(!this.filterForm.valid) {
      console.error(this.filterForm.errors);
      return;
    }

    const filters = this.filterForm.value as LotImagesFiltersFormValues;
    this.dialogRef.close(filters);
  }
  
  isSubmitButtonDisabled(): boolean {
    return this.filterForm.invalid || this.isLoading;
  }
  
  get studentControl() {
    return this.filterForm.get("student");
  }
}
