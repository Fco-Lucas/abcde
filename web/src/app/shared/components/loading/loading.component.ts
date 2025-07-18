import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// Interface para os dados que o dialog receberá
export interface LoadingData {
  message: string;
}

@Component({
  selector: 'app-loading',
  standalone: true,
  imports: [MatDialogModule, MatProgressSpinnerModule],
  templateUrl: './loading.component.html',
})
export class LoadingComponent {
  // Injeta os dados passados para o dialog e os torna públicos para o template
  constructor(@Inject(MAT_DIALOG_DATA) public data: LoadingData) {}
}