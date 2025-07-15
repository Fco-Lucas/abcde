import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map, take } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const publicGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated$.pipe(
    take(1), // Pega o valor mais recente e completa
    map(isAuthenticated => {
      // Se o usuário ESTÁ autenticado...
      if (isAuthenticated) {
        router.navigate(['/app/home']); 
        return false;
      }
      
      // Se o usuário NÃO ESTÁ autenticado, permite o acesso.
      return true;
    })
  );
};