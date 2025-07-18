import { Injectable } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { Subject } from 'rxjs';

@Injectable()
export class CustomPaginatorIntl implements MatPaginatorIntl {
  // O 'changes' é um Subject que notifica o paginator quando as labels mudam.
  changes = new Subject<void>();

  // Tradução das labels fixas
  firstPageLabel = `Primeira página`;
  itemsPerPageLabel = `Itens por página:`;
  lastPageLabel = `Última página`;
  nextPageLabel = 'Próxima página';
  previousPageLabel = 'Página anterior';

  // Este método formata a label que mostra o intervalo de itens (ex: "1 – 10 de 100")
  getRangeLabel(page: number, pageSize: number, length: number): string {
    // Caso não haja itens
    if (length === 0) return `Página 1 de 1`;
    
    // Calcula o número total de páginas
    const totalPages = Math.ceil(length / pageSize);
    
    // Retorna a string no formato desejado
    return `Página ${page + 1} de ${totalPages}`;
  }
}