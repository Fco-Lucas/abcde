import { ChangeDetectionStrategy, Component, inject, signal, ViewChild } from '@angular/core';

import { MatIconModule } from '@angular/material/icon';
import { ClientFiltersComponent, type ClientFiltersFormValues } from '../../components/client-filters/client-filters.component';
import { ClientListComponent } from '../../components/client-list.component/client-list.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { DialogCreateClientComponent } from '../../components/dialog-create-client/dialog-create-client.component';

import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-clients-page.component',
  imports: [
    CommonModule,
    ClientListComponent,
    ClientFiltersComponent,
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './clients-page.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ClientsPageComponent {
  // Criação de um novo cliente
  readonly dialog = inject(MatDialog);

  openCreateClientDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogRef = this.dialog.open(DialogCreateClientComponent, {
      width: '500px',
      enterAnimationDuration: enterAnimationDuration,
      exitAnimationDuration: exitAnimationDuration,
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.clientListComponent.loadClientsPage();
    });
  }

  // Exibição da lista dos clientes
  public hasLoadError = signal(false);
  public currentFilters = signal<Partial<ClientFiltersFormValues> | null>(null);

  @ViewChild('clientList') private clientListComponent!: ClientListComponent;

  onLoadStatusChanged(status: 'SUCCESS' | 'ERROR'): void {
    this.hasLoadError.set(status === "ERROR");
  }

  onFilterSubmit(filters: ClientFiltersFormValues): void {
    // Atualiza o signal com os novos filtros
    this.currentFilters.set(filters);
  }
}
