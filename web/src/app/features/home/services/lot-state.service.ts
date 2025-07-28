import { Injectable, signal } from '@angular/core';
import { LotInterface } from '../models/lot.model';
import type { PermissionInterface } from '../../permissions/models/permission.model';

@Injectable({
  providedIn: 'root'
})
export class LotStateService {
  // Usamos um signal para armazenar o lote selecionado.
  // Ele começa como null, pois nenhum lote está selecionado inicialmente.
  public readonly selectedLot = signal<LotInterface | null>(null);
  public readonly userPermissions = signal<PermissionInterface | null>(null);

  /**
   * Define o lote que foi selecionado pelo usuário.
   * @param lot O objeto completo do lote a ser armazenado.
   */
  selectLot(lot: LotInterface): void {
    this.selectedLot.set(lot);
  }

  setPermissions(permissions: PermissionInterface) {
    this.userPermissions.set(permissions);
  }

  /**
   * Limpa o lote selecionado. É uma boa prática chamar isso
   * quando o usuário sai da página de detalhes.
   */
  clearSelection(): void {
    this.selectedLot.set(null);
    this.userPermissions.set(null);
  }
}
