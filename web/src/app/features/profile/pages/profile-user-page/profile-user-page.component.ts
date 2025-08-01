import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';

import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { switchMap, tap, catchError, of, combineLatest } from 'rxjs';

import { AuthService } from '../../../../core/services/auth.service';
import { ClientUsersService } from '../../../clients/services/client-users.service';
import { ClientUserInterface } from '../../../clients/models/clientUsers.model';
import { DialogUpdateUserInfoComponent, type DataDialogUpdateClientUserInfoInterface } from '../../components/dialog-update-user-info/dialog-update-user-info.component';
import { DialogUpdateUserPasswordComponent, type DataDialogUpdateClientUserPasswordInterface } from '../../components/dialog-update-user-password.component/dialog-update-user-password.component';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';


interface ProfileUserState {
  clientUser: ClientUserInterface | null;
  loading: boolean;
  error: string | null;
}

interface ProfileUserQuery {
  reload: number;
}

@Component({
  selector: 'app-profile-user-page',
  standalone: true,
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
export class ProfileUserPageComponent {
  private authService = inject(AuthService);
  private clientUserService = inject(ClientUsersService);
  private dialog = inject(MatDialog);

  // ✅ 1. UM ÚNICO SIGNAL para controlar os gatilhos
  private query = signal<ProfileUserQuery>({ reload: 0 });

  private state = signal<ProfileUserState>({
    clientUser: null,
    loading: true,
    error: null,
  });

  // ✅ 2. Signals COMPUTADOS públicos e `readonly` para a View
  public readonly clientUser = computed(() => this.state().clientUser);
  public readonly isLoading = computed(() => this.state().loading);
  public readonly error = computed(() => this.state().error);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoading() && !this.clientUser()) return 'loading';
    if (this.error()) return 'error';
    return 'success';
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
        return this.clientUserService.getClientUserById("999", userId);
      }),
      catchError(err => {
        console.error("Erro ao carregar perfil do usuário:", err);
        const errorMessage = err.message || "Não foi possível carregar seu perfil.";
        this.state.update(s => ({ ...s, loading: false, error: errorMessage }));
        return of(null); // Para o fluxo não quebrar
      })
    ).subscribe(user => {
      if (user) {
        this.state.set({
          clientUser: user,
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
    const currentUser = this.clientUser();
    if (!currentUser) return;

    const dialogData: DataDialogUpdateClientUserInfoInterface = { clientUser: currentUser };
    this.dialog.open(DialogUpdateUserInfoComponent, { width: '500px', data: dialogData })
      .afterClosed().subscribe(result => {
        if (result) this.forceReload();
      });
  }

  openDialogUpdateClientPassword(): void {
    const currentUser = this.clientUser();
    if (!currentUser) return;

    const dialogData: DataDialogUpdateClientUserPasswordInterface = {
      clientUserId: currentUser.id,
      clientId: currentUser.clientId,
    };
    this.dialog.open(DialogUpdateUserPasswordComponent, { width: '500px', data: dialogData });
  }
}