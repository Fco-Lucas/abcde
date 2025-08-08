import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

export const profileGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const decoded = authService.getDecodedToken();
  
  if (!decoded) {
    router.navigate(['/app/auth/login']);
    return false;
  }

  const isCnpj = /^\d{14}$/.test(decoded.sub);

  if (isCnpj) {
    router.navigate(['/app/profile/client']);
  } else {
    router.navigate(['/app/profile/user']);
  }

  return false; // evita carregar a rota intermediária
};
