import { Component, inject, signal } from '@angular/core';
import { RegisterFormComponent } from '../../components/register-form/register-form.component';
import { RegisterFormValues } from '../../models/register.model';
import { Router } from '@angular/router';
import type { CreateClient } from '../../../clients/models/client.model';
import { ClientService } from '../../../clients/services/client.service';
import { finalize } from 'rxjs';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-register-page',
  imports: [RegisterFormComponent],
  templateUrl: './register-page.component.html',
})
export class RegisterPageComponent {
  public isLoading = signal(false);
  private router = inject(Router);
  private clientService = inject(ClientService);
  private notification = inject(NotificationService);

  handleRegisterSubmit(formValues: RegisterFormValues): void {
    this.isLoading.set(true);

    const data: CreateClient = {
      name: formValues.name || "",
      cnpj: formValues.cnpj || "",
      password: formValues.password || ""
    }

    this.clientService.createClient(data).pipe(
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Cliente cadastrado com sucesso, realize o login com as credenciais informadas");
        this.router.navigateByUrl("/auth/login");
      },
      error: (err: Error) => {
        this.notification.showError(err.message);
        console.error('Erro no login:', err.message);
      },
    });
  }
}
