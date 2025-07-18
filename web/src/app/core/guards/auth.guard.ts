import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map, take, type Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state): Observable<boolean> | Promise<boolean> | boolean => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Usar o Observable isAuthenticated$ para uma verificação reativa
  return authService.isAuthenticated$.pipe(
    take(1), // Pega o valor mais recente e completa
    map(isAuthenticated => {
      if (isAuthenticated) {
        return true;
      } else {
        router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
        return false;
      }
    })
  );
};
