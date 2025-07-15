import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map, take } from 'rxjs';
import { NotificationService } from '../services/notification.service';
import { AuthService, AuthenticatedUserRole } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);

  // 1. Pega os cargos permitidos da propriedade 'data' da rota
  const allowedRoles = route.data?.['roles'] as AuthenticatedUserRole[];

  // Se a rota não especificar nenhum cargo, bloqueia por segurança.
  if (!allowedRoles || allowedRoles.length === 0) {
    console.error('Rota sem a propriedade `data.roles` definida.');
    return false;
  }

  // 2. Usa o Observable do AuthService para pegar o cargo do usuário
  return authService.currentUserRole$.pipe(
    take(1),
    map(userRole => {
      // 3. Verifica se o cargo do usuário está na lista de cargos permitidos
      if (userRole && allowedRoles.includes(userRole)) {
        return true; // Acesso permitido!
      } else {
        // Acesso negado!
        notificationService.showError('Você não tem permissão para acessar esta página.');
        
        // Redireciona para uma página segura (como a home)
        router.navigate(['/app/home']); 
        
        return false;
      }
    })
  );
};