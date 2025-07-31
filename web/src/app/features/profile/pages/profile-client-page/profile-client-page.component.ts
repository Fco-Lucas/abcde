import { CommonModule } from '@angular/common';
import { Component, computed, inject, signal, type OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService, type AuthenticatedUserRole } from '../../../../core/services/auth.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { ClientService } from '../../../clients/services/client.service';
import { Client } from '../../../clients/models/client.model';
import { DialogUpdateClientInfoComponent, type DataDialogUpdateClientInfoInterface } from '../../components/dialog-update-client-info/dialog-update-client-info.component';
import { DialogUpdateClientPasswordComponent, type DataDialogUpdateClientPasswordInterface } from '../../components/dialog-update-client-password/dialog-update-client-password.component';
import { NgxMaskPipe } from 'ngx-mask';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

interface ProfileClientState {
  client: Client | null;
  loading: boolean;
  error: string | null
}

@Component({
  selector: 'app-profile-page',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule,
    NgxMaskPipe,
    UiErrorComponent,
    MatProgressSpinnerModule
  ],
  templateUrl: './profile-client-page.component.html',
})
export class ProfileClientPageComponent implements OnInit {
  readonly dialog = inject(MatDialog);
  private authService = inject(AuthService);
  private clientService = inject(ClientService);

  private state = signal<ProfileClientState>({
    client: null, 
    loading: true, // Inicia como true para a carga inicial
    error: null,
  });
  
  // Obtem o ID e o cargo do usuário autenticado
  authUserId = toSignal(this.authService.currentUserId$);
  authUserRole = toSignal(this.authService.currentUserRole$);
  
  public client = computed(() => this.state().client);
  public isLoading = computed(() => this.state().loading);
  public error = computed(() => this.state().error);

  ngOnInit(): void {
    this.loadInitialInfo();
  }

  loadInitialInfo(): void {
    this.state.update(s => ({ client: null, loading: true, error: null }));

    const authUserId = this.authUserId();
    if(!authUserId) {
      console.error(`ID do usuário autenticado não encontrado`);
      this.state.update(s => ({ ...s, error: "Ocorreu um erro, tente novamente mais tarde" }));
      return;
    }

    this.clientService.getClientById(authUserId).subscribe({
      next: (data) => {
        this.state.update(s => ({ client: data, loading: false, error: null}));
      },
      error: (err) => {
        console.error(`Erro ao buscar informações do usuário: ${err.message}`);
        this.state.update(s => ({ client: null, loading: false, error: "Ocorreu um erro ao buscar as informações do seu usuário, tente novamente mais tarde" }));
      }
    });
  }

  openDialogUpdateClientInfo() {
    const client = this.client();
    if(!client) return;

    const dialogData: DataDialogUpdateClientInfoInterface = {
      client: client
    };

    const dialogRef = this.dialog.open(DialogUpdateClientInfoComponent, {
      width: '500px',
      data: dialogData,
    });

    dialogRef.afterClosed().subscribe(result => { 
      if(result) this.loadInitialInfo();
    });
  }

  openDialogUpdateClientPassword() {
    const client = this.client();
    if(!client) return;

    const dialogData: DataDialogUpdateClientPasswordInterface = {
      clientId: client.id
    };

    this.dialog.open(DialogUpdateClientPasswordComponent, {
      width: '500px',
      data: dialogData,
    });
  }

  getClientRole(): string {
    const authUserRole: AuthenticatedUserRole | undefined = this.authUserRole();

    if(authUserRole === "COMPUTEX") return "COMPUTEX";
    else if(authUserRole === "CLIENT") return "Cliente";
    else if(authUserRole === "CLIENT_USER") return "Usuário";
    else return "";
  }
}
