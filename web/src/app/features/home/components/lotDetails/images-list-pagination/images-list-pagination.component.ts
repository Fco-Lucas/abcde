import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-images-list-pagination',
  imports: [
    MatButtonModule,
    MatIconModule,
    CommonModule
  ],
  templateUrl: './images-list-pagination.component.html',
})
export class ImagesListPaginationComponent {
  @Input() currentPage: number = 0;
  @Input() totalPages: number = 0;
  @Input() pageSize: number = 10;

  // Evento que será emitido quando a página mudar
  @Output() pageChange = new EventEmitter<number>();

  /**
   * Navega para a primeira página (página 0).
   * Emite o evento pageChange se não estiver já na primeira página.
   */
  goToFirstPage(): void {
    if (this.currentPage > 0) { // Condição para 0-indexado
      this.pageChange.emit(0); // Emite 0 para a primeira página
    }
  }

  /**
   * Navega para a página anterior.
   * Emite o evento pageChange se não estiver já na primeira página.
   */
  goToPreviousPage(): void {
    if (this.currentPage > 0) { // Condição para 0-indexado
      this.pageChange.emit(this.currentPage - 1);
    }
  }

  /**
   * Navega para a próxima página.
   * Emite o evento pageChange se não estiver já na última página.
   */
  goToNextPage(): void {
    // Condição para 0-indexado: currentPage deve ser menor que (totalPages - 1)
    if (this.currentPage < this.totalPages - 1) {
      this.pageChange.emit(this.currentPage + 1);
    }
  }

  /**
   * Navega para a última página.
   * Emite o evento pageChange se não estiver já na última página.
   */
  goToLastPage(): void {
    // Garante que totalPages é maior que 0 antes de tentar calcular a última página
    // Condição para 0-indexado: currentPage deve ser menor que (totalPages - 1)
    if (this.totalPages > 0 && this.currentPage < this.totalPages - 1) {
      this.pageChange.emit(this.totalPages - 1); // Emite (totalPages - 1) para a última página
    }
  }

  /**
   * Getter para desabilitar o botão "Primeira Página" e "Página Anterior".
   * Retorna true se a página atual for a primeira (0) ou se não houver páginas.
   */
  get isFirstPage(): boolean {
    return this.currentPage === 0 || this.totalPages === 0; // Condição para 0-indexado
  }

  /**
   * Getter para desabilitar o botão "Próxima Página" e "Última Página".
   * Retorna true se a página atual for a última ou se não houver páginas.
   */
  get isLastPage(): boolean {
    // Condição para 0-indexado: currentPage é a última página se for (totalPages - 1)
    return this.currentPage === this.totalPages - 1 || this.totalPages === 0;
  }
}
