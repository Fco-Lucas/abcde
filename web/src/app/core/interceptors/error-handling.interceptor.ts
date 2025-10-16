import { HttpInterceptorFn, type HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const errorHandlingInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Ocorreu um erro desconhecido.';
      
      if (error.status === 401) {
        console.warn('Sessão expirada ou token inválido. Fazendo logout...');
        authService.logout(true);
        errorMessage = 'Sua sessão expirou. Faça login novamente.';
      } else if (error.error instanceof ErrorEvent) {
      // 1. Erro do lado do cliente (problema de rede, Angular, etc.)
        errorMessage = `Erro de conexão: ${error.error.message}`;
      } else {
        // 2. Erro do lado do servidor (a API respondeu com status 4xx ou 5xx)
        if (error.error && typeof error.error.message === 'string') {
          errorMessage = error.error.message;
        } else {
          errorMessage = `Ocorreu um erro no servidor (código ${error.status}). Por favor, tente novamente mais tarde.`;
        }
      }
      
      console.error(`Erro capturado pelo interceptor: ${errorMessage}`, error);
      
      // Retorna o erro para o .subscribe() do componente com a mensagem formatada.
      return throwError(() => new Error(errorMessage));
    })
  );
};