import { Component, inject, type OnInit } from '@angular/core';
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

@Component({
  selector: 'app-profile-user-page',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule
  ],
  templateUrl: './profile-user-page.component.html',
})
export class ProfileUserPageComponent implements OnInit {
  readonly dialog = inject(MatDialog);
  private authService = inject(AuthService);
  private clientUserService = inject(ClientUsersService);
  
  clientUserId = toSignal(this.authService.currentUserId$);
  clientUserRole = toSignal(this.authService.currentUserRole$);
  public clientUser!: ClientUserInterface; 

  ngOnInit(): void {
    this.loadInitialInfo();
  }

  loadInitialInfo(): void {
    const clientUserId = this.clientUserId();
    if(!clientUserId) return console.error(`ClientUserId não encontrado`);
    this.clientUserService.getClientUserById("999", clientUserId).subscribe({
      next: (data) => {
        this.clientUser = data;
      },
      error: (err) => {
        console.error(`Erro ao buscar informações do usuário: ${err.message}`);
      }
    });
  }

  openDialogUpdateClientInfo() {
    const dialogData: DataDialogUpdateClientUserInfoInterface = {
      clientUser: this.clientUser
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
    const dialogData: DataDialogUpdateClientUserPasswordInterface = {
      clientUserId: this.clientUser.id,
      clientId: this.clientUser.clientId,
    };

    this.dialog.open(DialogUpdateUserPasswordComponent, {
      width: '500px',
      data: dialogData,
    });
  }
}
