import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, OnInit, Output, type OnChanges, type SimpleChanges } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { lotImageInterface } from '../../../models/lot-image.models';
import { LotInterface } from '../../../models/lot.model';
import { LotImageService } from '../../../services/lot-image.service';
import { MatIconModule } from '@angular/material/icon';
import type { LotImagesFiltersFormValues } from '../dialog-filter-images/dialog-filter-images.component';

@Component({
  selector: 'app-images-list',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule
  ],
  templateUrl: './images-list.component.html',
  styleUrl: './images-list.component.scss'
})
export class ImagesListComponent implements OnChanges {
  private lotImageService = inject(LotImageService);

  public images: lotImageInterface[] | [] = [];
  
  @Input() filters: Partial<LotImagesFiltersFormValues> | null = null;
  @Input() lote!: LotInterface;
  @Input() currentPage: number = 0;
  @Input() pageSize: number = 10;
  @Input() selectedImageId: number | null = null;

  @Output() totalPagesChange = new EventEmitter<number>()
  @Output() imageSelected = new EventEmitter<number>();
  @Output() imagesLoaded = new EventEmitter<lotImageInterface[]>();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filters'] || changes['lote'] || changes['currentPage'] || changes['pageSize']) {
      if (this.lote) {
        this.loadImages();
      }
    }
  }

  loadImages(): void {
     if (!this.lote.id) {
      console.warn("Lote ID não disponível para carregar imagens.");
      this.images = []; // Limpa a lista se não houver ID
      this.totalPagesChange.emit(0);
      return;
    }

    this.lotImageService.getAllLotImages(this.currentPage, this.pageSize, this.lote.id, this.filters?.student).subscribe({
      next: (response) => {
        this.images = response.content;
        this.totalPagesChange.emit(response.totalPages);
        this.imagesLoaded.emit(this.images);
      },
      error: (err) => {
        console.error("Erro ao carregar imagens do lote:", err);
        this.totalPagesChange.emit(0);
        this.images = [];
        this.imagesLoaded.emit(this.images);
      }
    });
  }

  /**
   * Manipula o clique em uma miniatura de imagem.
   * Emite o ID da imagem clicada para o componente pai.
   * @param image O objeto da imagem clicada.
   */
  onImageClick(imageId: number): void {
    this.imageSelected.emit(imageId);
  }

  /**
   * Verifica se o card da imagem deve ter a classe 'active'.
   * @param imageId O ID da imagem do card.
   * @returns True se a imagem for a selecionada, caso contrário, false.
   */
  isActive(imageId: number): boolean {
    return this.selectedImageId === imageId;
  }
}
