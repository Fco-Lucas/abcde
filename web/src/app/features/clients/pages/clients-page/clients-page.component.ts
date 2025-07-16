import { ChangeDetectionStrategy, Component, inject, signal, ViewChild } from '@angular/core';

import { MatIconModule } from '@angular/material/icon';
import { ClientFiltersComponent, type ClientFiltersFormValues } from '../../components/client-filters/client-filters.component';
import { ClientListComponent } from '../../components/client-list.component/client-list.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { DialogCreateClientComponent } from '../../components/dialog-create-client/dialog-create-client.component';

import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { LoadingService } from '../../../../core/services/loading.service';
import { ClientService } from '../../services/client.service';
import { NotificationService } from '../../../../core/services/notification.service';
import type { CreateClient } from '../../models/client.model';

interface CreateFormValues {
  name: string,
  cnpj: string,
  password: string
}

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
  private loadingService = inject(LoadingService);
  private clientService = inject(ClientService);
  private notification = inject(NotificationService);

  @ViewChild('clientList') private clientListComponent!: ClientListComponent;

  openCreateClientDialog(enterAnimationDuration: string, exitAnimationDuration: string, initialData: CreateFormValues | null = null): void {
    const dialogRef = this.dialog.open(DialogCreateClientComponent, {
      width: '500px',
      data: initialData,
      enterAnimationDuration: enterAnimationDuration,
      exitAnimationDuration: exitAnimationDuration,
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.createClient(result as CreateFormValues);
      }
    });
  }

  createClient(formValues: CreateFormValues): void {
    this.loadingService.showLoad('Criando cliente, por favor aguarde...');

    const data: CreateClient = {
      name: formValues.name || "",
      cnpj: formValues.cnpj || "",
      password: formValues.password || ""
    }

    this.clientService.createClient(data).subscribe({
      next: (_) => {
        this.clientListComponent.loadClientsPage();
        this.loadingService.hideLoad();
        this.notification.showSuccess("Cliente criado com sucesso!");
      },
      error: (err) => {
        this.loadingService.hideLoad();
        this.notification.showError(err.message);
        this.openCreateClientDialog('0ms', '0ms', formValues);
      }
    });
  }

  // Exibição da lista dos clientes
  public hasLoadError = signal(false);
  public currentFilters = signal<Partial<ClientFiltersFormValues> | null>(null);

  onLoadStatusChanged(status: 'SUCCESS' | 'ERROR'): void {
    this.hasLoadError.set(status === "ERROR");
  }

  onFilterSubmit(filters: ClientFiltersFormValues): void {
    // Atualiza o signal com os novos filtros
    this.currentFilters.set(filters);
  }
}
