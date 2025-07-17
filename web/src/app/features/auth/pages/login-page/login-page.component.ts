import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, take } from 'rxjs';
import { LoginFormComponent } from '../../components/login-form/login-form.component';
import { AuthService } from '../../../../core/services/auth.service';
import { LoginFormValues } from '../../models/login.model';
import { AuthPayload } from '../../../../core/models/auth.model';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-login-page',
  imports: [LoginFormComponent],
  templateUrl: './login-page.component.html',
})
export class LoginPageComponent {
  // 1. Usar signal para o estado de carregamento
  public isLoading = signal(false);

  private returnUrl = "/app/home";
  private authService = inject(AuthService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private notification = inject(NotificationService);

  ngOnInit(): void {
    // Pega a URL de retorno de forma segura
    this.route.queryParams.pipe(take(1)).subscribe(params => {
      this.returnUrl = params['returnUrl'] || '/app/home';
    });
  }

  handleLoginSubmit(formValues: LoginFormValues): void {
    this.isLoading.set(true);

    const credentials: AuthPayload = {
      login: formValues.login ?? "",
      password: formValues.password ?? ""
    };

    this.authService.login(credentials).pipe(
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: () => {
        this.router.navigateByUrl(this.returnUrl);
      },
      error: (err: Error) => {
        // 3. `errorMessage` removido, usamos `err.message` diretamente
        this.notification.showError(err.message || "Falha no login.");
        console.error('Erro no login:', err.message);
      },
    });
  }
}