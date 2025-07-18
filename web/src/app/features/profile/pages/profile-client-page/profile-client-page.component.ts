import { CommonModule } from '@angular/common';
import { Component, inject, type OnInit } from '@angular/core';
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

@Component({
  selector: 'app-profile-page',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule,
    NgxMaskPipe
  ],
  templateUrl: './profile-client-page.component.html',
})
export class ProfileClientPageComponent implements OnInit {
  readonly dialog = inject(MatDialog);
  private authService = inject(AuthService);
  private clientService = inject(ClientService);
  
  clientId = toSignal(this.authService.currentUserId$);
  clientRole = toSignal(this.authService.currentUserRole$);
  public client!: Client; 

  ngOnInit(): void {
    this.loadInitialInfo();
  }

  loadInitialInfo(): void {
    const clientId = this.clientId();
    if(!clientId) return console.error(`ClienteId não encontrado`);
    this.clientService.getClientById(clientId).subscribe({
      next: (data) => {
        this.client = data;
      },
      error: (err) => {
        console.error(`Erro ao buscar informações do usuário: ${err.message}`);
      }
    });
  }

  openDialogUpdateClientInfo() {
    const dialogData: DataDialogUpdateClientInfoInterface = {
      client: this.client
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
    const dialogData: DataDialogUpdateClientPasswordInterface = {
      clientId: this.client.id
    };

    this.dialog.open(DialogUpdateClientPasswordComponent, {
      width: '500px',
      data: dialogData,
    });
  }

  getClientRole(): string {
    const clientRole: AuthenticatedUserRole | undefined = this.clientRole();

    if(clientRole === "COMPUTEX") return "COMPUTEX";
    else if(clientRole === "CLIENT") return "Cliente";
    else if(clientRole === "CLIENT_USER") return "Usuário";
    else return "";
  }
}
