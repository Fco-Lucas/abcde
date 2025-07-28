import { Component, inject, signal, ViewChild, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { toSignal } from '@angular/core/rxjs-interop';

import { LotFiltersComponent, LotFiltersFormValues } from '../../components/lot-filters/lot-filters.component';
import { LotListComponent } from '../../components/lot-list/lot-list.component';
import { DialogCreateLotComponent, DialogCreateLotData } from '../../components/dialog-create-lot/dialog-create-lot.component';
import { DialogImageUploadProgressComponent, DialogImageUploadProgressData } from '../../components/dialog-image-upload-progress/dialog-image-upload-progress.component';
import { AuthService, type AuthenticatedUserRole } from '../../../../core/services/auth.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LotInterface } from '../../models/lot.model';
import { PermissionsService } from '../../../permissions/services/permissions.service';
import type { PermissionInterface } from '../../../permissions/models/permission.model';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    LotFiltersComponent,
    LotListComponent
  ],
  templateUrl: './home-page.component.html',
})
export class HomePageComponent implements OnInit {
  // Injeção de dependências
  readonly dialog = inject(MatDialog);
  private authService = inject(AuthService);
  private notification = inject(NotificationService); // Injetar para notificações
  private permissionsService = inject(PermissionsService);

  // Sinais e propriedades
  userIdSignal = toSignal(this.authService.currentUserId$);
  userId!: string;

  userRoleSignal = toSignal(this.authService.currentUserRole$);
  userRole!: AuthenticatedUserRole;

  userPermissions!: PermissionInterface;
  public hasLoadError = signal(false);
  public currentFilters = signal<Partial<LotFiltersFormValues> | null>(null);

  @ViewChild('lotList') private lotListComponent!: LotListComponent;

  ngOnInit(): void {
    const userId = this.userIdSignal();
    if(!userId) {
      console.error(`Erro ao encontrar ID do usuário`);
      return;
    }
    this.userId = userId;

    const userRole = this.userRoleSignal();
    if(!userRole) {
      console.error(`Erro ao encontrar cargo do usuário`);
      return;
    }
    this.userRole = userRole;

    this.permissionsService.getUserPermission(this.userId).subscribe({
      next: (permissions) => {
        this.userPermissions = permissions;
      },
      error: (err) => {
        console.error(`Erro ao buscar as permissões do usuário autenticado: ${err.message}`);
      }
    });
  }

  /**
   * 1. Orquestra todo o fluxo de criação do lote.
   */
  openCreateLotDialog(): void {
    const dialogData: DialogCreateLotData = { userId: this.userId };

    const createDialogRef = this.dialog.open(DialogCreateLotComponent, {
      width: '500px',
      data: dialogData
    });

    // Escuta o resultado do primeiro diálogo
    createDialogRef.afterClosed().subscribe(result => {
      // Se o usuário cancelou ou não retornou dados, não faz nada
      if (!result) return;

      const { lot, images } = result;

      // Se houver imagens para enviar, abre o segundo diálogo
      if (images && images.length > 0) {
        this.openProgressDialog(lot, images);
      } else {
        // Se não houver imagens, o processo terminou. Notifica e atualiza a lista.
        this.notification.showSuccess("Lote criado com sucesso!");
        this.lotListComponent.loadLotsPage();
      }
    });
  }

  /**
   * 2. Abre o diálogo de progresso de upload e atualiza a lista ao final.
   */
  private openProgressDialog(lot: LotInterface, images: FileList): void {
    const dialogData: DialogImageUploadProgressData = { lotId: lot.id, images };

    const progressDialogRef = this.dialog.open(DialogImageUploadProgressComponent, {
      width: '500px',
      data: dialogData,
      disableClose: true // Impede o fechamento acidental
    });

    // O processo SÓ termina de verdade quando o diálogo de progresso fecha.
    // É AQUI que a lista deve ser atualizada.
    progressDialogRef.afterClosed().subscribe(() => {
      this.lotListComponent.loadLotsPage();
    });
  }

  // Métodos de controle do template (sem alterações)
  onLoadStatusChanged(status: 'SUCCESS' | 'ERROR'): void {
    this.hasLoadError.set(status === "ERROR");
  }

  onFilterSubmit(filters: LotFiltersFormValues): void {
    this.currentFilters.set(filters);
  }
}
