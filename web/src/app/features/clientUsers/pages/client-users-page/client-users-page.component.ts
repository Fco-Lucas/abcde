import { Component, inject, OnInit, signal, ViewChild } from '@angular/core';
import { ClientUsersListComponent } from '../../components/client-users-list/client-users-list.component';
import { ActivatedRoute } from '@angular/router';
import { PermissionInterface } from '../../../permissions/models/permission.model';
import { PermissionsService } from '../../../permissions/services/permissions.service';
import { ClientUsersFiltersComponent, ClientUsersFiltersFormInterface } from '../../components/client-users-filters/client-users-filters.component';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { DialogCreateClientUserComponent, type ClientUsersCreateFormValues, type CreateUserDialogData } from '../../components/dialog-create-client-user/dialog-create-client-user.component';
import { MatDialog } from '@angular/material/dialog';
import { ClientService } from '../../../clients/services/client.service';
import type { Client } from '../../../clients/models/client.model';

@Component({
  selector: 'app-client-users-page',
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    ClientUsersFiltersComponent,
    ClientUsersListComponent,
    MatCardModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './client-users-page.component.html',
})
export class ClientUsersPageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private permissionService = inject(PermissionsService);
  private clientService = inject(ClientService);
  public clientId: string = "";
  public clientData: Client | null = null;
  public permissions: PermissionInterface[] = [];
  readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.clientId = params.get('id') ?? '';
    });

    // Busca as permissões
    this.permissionService.getPermissions().subscribe({
      next: (permissions) => {
        this.permissions = permissions;
      },
      error: (error) => {
        console.error(error.message);
      }
    });

    // Busca as informações do cliente
    this.clientService.getClientById(this.clientId).subscribe({
      next: (client) => {
        this.clientData = client;
      },
      error: (error) => {
        console.error(error.message);
      }
    })
  }

  openCreateClientUserDialog(initialData: ClientUsersCreateFormValues | null = null): void {
    const dialogData = {
      initialData: initialData,
      clientId: this.clientData!.id,
      clientName: this.clientData!.name,
      permissions: this.permissions
    } as CreateUserDialogData;

    const dialogRef = this.dialog.open(DialogCreateClientUserComponent, {
      width: '500px',
      data: dialogData,
    });

    // 2. Escuta o evento de fechamento
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.createClientUser(result as ClientUsersCreateFormValues);
      }
    });
  }

  // Criação dos usuários
  createClientUser(data: ClientUsersCreateFormValues): void {
    console.log(data);
  }

  // Exibição da lista dos usuários
  public hasLoadError = signal(false);
  public currentFilters = signal<Partial<ClientUsersFiltersFormInterface> | null>(null);

  @ViewChild('clientList') private clientUsersListComponent!: ClientUsersListComponent;

  onLoadStatusChanged(status: 'SUCCESS' | 'ERROR'): void {
    this.hasLoadError.set(status === "ERROR");
  }

  onFilterSubmit(filters: ClientUsersFiltersFormInterface): void {
    // Atualiza o signal com os novos filtros
    this.currentFilters.set(filters);
  }
}
