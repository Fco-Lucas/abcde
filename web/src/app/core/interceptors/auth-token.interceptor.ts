import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';

export const authToken: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  // Não interceptar a requisição de login ou se não houver token
  if (req.url.includes('/auth') || !token) {
    return next(req);
  }

  // Clona a requisição e adiciona o header de autorização
  const clonedReq = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`),
  });

  return next(clonedReq);
};
