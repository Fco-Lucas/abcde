import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';

import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { switchMap, tap, catchError, of, combineLatest } from 'rxjs';

import { AuthService } from '../../../../core/services/auth.service';
import { ClientService } from '../../../clients/services/client.service';
import { Client } from '../../../clients/models/client.model';
import { DialogUpdateClientInfoComponent, DataDialogUpdateClientInfoInterface } from '../../components/dialog-update-client-info/dialog-update-client-info.component';
import { DialogUpdateClientPasswordComponent, DataDialogUpdateClientPasswordInterface } from '../../components/dialog-update-client-password/dialog-update-client-password.component';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NgxMaskPipe } from 'ngx-mask';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

interface ProfileClientState {
  client: Client | null;
  loading: boolean;
  error: string | null;
}

interface ProfileQuery {
  reload: number;
}

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule,
    NgxMaskPipe,
    UiErrorComponent,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTooltipModule
  ],
  templateUrl: './profile-client-page.component.html',
})
export class ProfileClientPageComponent {
  private authService = inject(AuthService);
  private clientService = inject(ClientService);
  private dialog = inject(MatDialog);

  private query = signal<ProfileQuery>({ reload: 0 });

  private state = signal<ProfileClientState>({
    client: null,
    loading: true,
    error: null,
  });

  private readonly authUserRole = toSignal(this.authService.currentUserRole$);
  public readonly client = computed(() => this.state().client);
  public readonly isLoading = computed(() => this.state().loading);
  public readonly error = computed(() => this.state().error);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoading()) return 'loading';
    if (this.error()) return 'error';
    return 'success';
  });

  public readonly clientRole = computed(() => {
    const role = this.authUserRole();
    if (role === "COMPUTEX") return "COMPUTEX";
    if (role === "CLIENT") return "Cliente";
    if (role === "CLIENT_USER") return "Usuário";
    return "";
  });

  constructor() {
    const userId$ = toObservable(toSignal(this.authService.currentUserId$));
    const query$ = toObservable(this.query);

    combineLatest([userId$, query$]).pipe(
      tap(() => this.state.update(s => ({ ...s, loading: true, error: null }))),
      switchMap(([userId, _query]) => {
        if (!userId) {
          throw new Error("ID do usuário autenticado não encontrado.");
        }
        return this.clientService.getClientById(userId);
      }),
      catchError(err => {
        console.error("Erro ao carregar perfil:", err);
        const errorMessage = err.message || "Não foi possível carregar seu perfil.";
        this.state.update(s => ({ ...s, loading: false, error: errorMessage }));
        return of(null); // Para o fluxo não quebrar
      })
    ).subscribe(client => {
      if (client) {
        this.state.set({
          client: client,
          loading: false,
          error: null,
        });
      }
    });
  }

  forceReload(): void {
    this.query.update(q => ({ ...q, reload: q.reload + 1 }));
  }

  openDialogUpdateClientInfo(): void {
    const currentClient = this.client();
    if (!currentClient) return;

    const dialogData: DataDialogUpdateClientInfoInterface = { client: currentClient };
    this.dialog.open(DialogUpdateClientInfoComponent, { width: '500px', data: dialogData })
      .afterClosed().subscribe(result => {
        if (result) this.forceReload();
      });
  }

  openDialogUpdateClientPassword(): void {
    const currentClient = this.client();
    if (!currentClient) return;

    const dialogData: DataDialogUpdateClientPasswordInterface = { clientId: currentClient.id };
    this.dialog.open(DialogUpdateClientPasswordComponent, { width: '500px', data: dialogData });
  }
}