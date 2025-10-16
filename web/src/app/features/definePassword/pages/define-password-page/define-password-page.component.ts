import { Component, inject, signal } from '@angular/core';
import { DefinePasswordFormComponent, type DefinePasswordFormValues } from '../../components/define-password-form/define-password-form.component';
import { CommonModule } from '@angular/common';
import { ClientUsersService } from '../../../clients/services/client-users.service';
import { ClientService } from '../../../clients/services/client.service';
import { AuthService, type TokenPayload } from '../../../../core/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from '../../../../core/services/notification.service';
import { jwtDecode } from 'jwt-decode';
import type { UpdateClientPasswordInterface } from '../../../clients/models/client.model';
import { finalize } from 'rxjs';
import type { UpdateClientUserPasswordInterface } from '../../../clients/models/clientUsers.model';

@Component({
  selector: 'app-define-password-page',
  imports: [CommonModule, DefinePasswordFormComponent],
  templateUrl: './define-password-page.component.html',
})
export class DefinePasswordPageComponent {
  public isLoading = signal(false);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private authService = inject(AuthService);
  private clientService = inject(ClientService);
  private clientUserService = inject(ClientUsersService);
  private notification = inject(NotificationService);

  private decodedToken: TokenPayload | null = null;

  constructor() {
    // Pega o parâmetro 'key' da URL
    const key = this.route.snapshot.queryParamMap.get('key');

    if (!key) {
      console.error("Nenhuma chave (token) foi fornecida na URL.");
      this.notification.showError("URL inválida ou expirada");
      this.router.navigate(['/']);
      return;
    }

    try {
      // Decodifica o token
      this.decodedToken = jwtDecode<TokenPayload>(key);

      // Verifica se o token expirou
      const expirationDate = this.decodedToken.exp! * 1000;
      if (expirationDate < Date.now()) {
        console.error("O link para redefinição de senha expirou.");
        this.notification.showError("Este link de alteração de senha não é válido. Solicite uma nova recuperação de senha.");
        this.router.navigate(['/']); // Navega para a página inicial se expirou
        return;
      }
    } catch (error) {
      console.error("Erro ao decodificar a chave (token inválido):", error);
      this.router.navigate(['/']); // Navega para a página inicial se a chave for inválida
    }
  }

  updateCustomerPassword(formValues: DefinePasswordFormValues): void {
    this.isLoading.set(true);

    const idCustomer = this.decodedToken!.id;
    const data: UpdateClientPasswordInterface = { ...formValues };

    this.clientService.updatePasswordClient(idCustomer, data).pipe(
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Senha atualizada com sucesso!");
        this.authService.logout(); // Navega para o login e remove tokens
      },
      error: (err) => {
        console.error(`Erro ao restaurar senha no servidor: ${err.message}`);
        this.notification.showError("Ocorreu um erro ao restaurar a senha, tente novamente mais tarde");
      }
    });
  }

  updateCustomerUserPassword(formValues: DefinePasswordFormValues): void {
    this.isLoading.set(true);

    const idCustomer = this.decodedToken!.idClient;
    const idCustomerUser = this.decodedToken!.id;
    const data: UpdateClientUserPasswordInterface = { ...formValues };

    this.clientUserService.updatePasswordClientUser(idCustomer, idCustomerUser, data).pipe(
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Senha atualizada com sucesso!");
        this.authService.logout(); // Navega para o login e remove tokens
      },
      error: (err) => {
        console.error(`Erro ao restaurar senha no servidor: ${err.message}`);
        this.notification.showError("Ocorreu um erro ao restaurar a senha, tente novamente mais tarde");
      }
    });
  }

  handleUpdatePassword(formValues: DefinePasswordFormValues): void {
    const subject = this.decodedToken!.sub;

    // Valida se tem apenas números e 14 caracteres
    const isValidCnpj = /^\d{14}$/.test(subject);
    if(isValidCnpj) {
      this.updateCustomerPassword(formValues);
      return;
    }

    this.updateCustomerUserPassword(formValues);
  }
}
