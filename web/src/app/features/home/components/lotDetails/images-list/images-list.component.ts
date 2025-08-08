import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { LotImagePageableInterface } from '../../../models/lot-image.models';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UiNotFoundComponent } from '../../../../../shared/components/ui-not-found/ui-not-found.component';

@Component({
  selector: 'app-images-list',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    UiNotFoundComponent
  ],
  templateUrl: './images-list.component.html',
  styleUrl: './images-list.component.scss'
})
export class ImagesListComponent {
  @Input() images: LotImagePageableInterface[] = [];
  @Input() isLoading: boolean = false;
  @Input() selectedImageId: number | null = null;
  @Input() createdByComputex: boolean = false;

  @Output() imageSelected = new EventEmitter<number>();
  @Output() viewAudit = new EventEmitter<number>();

  public isActive(imageId: number): boolean {
    return this.selectedImageId === imageId;
  }
}
