import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';

export const authTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  // Se tiver o header 'Skip-Interceptor', ignora completamente
  if (req.headers.has('Skip-Interceptor')) {
    const cleanReq = req.clone({
      headers: req.headers.delete('Skip-Interceptor') // remove o flag antes de enviar
    });
    return next(cleanReq);
  }

  const token = authService.getToken();

  // Não interceptar login ou requisições sem token
  if (req.url.includes('/auth') || !token) {
    return next(req);
  }

  // Clona e adiciona o Authorization padrão
  const clonedReq = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`),
  });

  return next(clonedReq);
};
