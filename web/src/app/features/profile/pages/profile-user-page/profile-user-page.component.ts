import { Component, computed, inject, signal, type OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../../../../core/services/auth.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { ClientUsersService } from '../../../clients/services/client-users.service';
import { ClientUserInterface } from '../../../clients/models/clientUsers.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { DialogUpdateUserInfoComponent, type DataDialogUpdateClientUserInfoInterface } from '../../components/dialog-update-user-info/dialog-update-user-info.component';
import { DialogUpdateUserPasswordComponent, type DataDialogUpdateClientUserPasswordInterface } from '../../components/dialog-update-user-password.component/dialog-update-user-password.component';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

interface ProfileUserState {
  clientUser: ClientUserInterface | null;
  loading: boolean;
  error: string | null;
}

@Component({
  selector: 'app-profile-user-page',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule,
    UiErrorComponent,
    MatProgressSpinnerModule
  ],
  templateUrl: './profile-user-page.component.html',
})
export class ProfileUserPageComponent implements OnInit {
  readonly dialog = inject(MatDialog);
  private authService = inject(AuthService);
  private clientUserService = inject(ClientUsersService);
  
  authUserId = toSignal(this.authService.currentUserId$);

  private state = signal<ProfileUserState>({
    clientUser: null,
    loading: true,
    error: null
  });

  public clientUser = computed(() => this.state().clientUser);
  public isLoading = computed(() => this.state().loading);
  public error = computed(() => this.state().error);

  ngOnInit(): void {
    this.loadInitialInfo();
  }

  loadInitialInfo(): void {
    this.state.set({ clientUser: null, loading: true, error: null });

    const authUserId = this.authUserId();
    if(!authUserId) {
      console.error(`ID do usuário autenticado não encontrado`);
      this.state.update(s => ({ ...s, error: "Ocorreu um erro, tente novamente mais tarde" }));
      return;
    }

    this.clientUserService.getClientUserById("999", authUserId).subscribe({
      next: (data) => {
        this.state.set({ clientUser: data, loading: false, error: null });
      },
      error: (err) => {
        console.error(`Erro ao buscar informações do usuário: ${err.message}`);
        this.state.set({ clientUser: null, loading: false, error: "Ocorreu um erro ao buscar as informações do seu usuário, tente novamente mais tarde" });
      }
    });
  }

  openDialogUpdateClientInfo() {
    const clientUser = this.clientUser();
    if(!clientUser) return;

    const dialogData: DataDialogUpdateClientUserInfoInterface = {
      clientUser: clientUser
    };

    const dialogRef = this.dialog.open(DialogUpdateUserInfoComponent, {
      width: '500px',
      data: dialogData,
    });

    dialogRef.afterClosed().subscribe(result => { 
      if(result) this.loadInitialInfo();
    });
  }

  openDialogUpdateClientPassword() {
    const clientUser = this.clientUser();
    if(!clientUser) return;

    const dialogData: DataDialogUpdateClientUserPasswordInterface = {
      clientUserId: clientUser.id,
      clientId: clientUser.clientId,
    };

    this.dialog.open(DialogUpdateUserPasswordComponent, {
      width: '500px',
      data: dialogData,
    });
  }
}
