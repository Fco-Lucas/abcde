import { HttpInterceptorFn, type HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorHandlingInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Ocorreu um erro desconhecido.';

      // 1. Erro do lado do cliente (problema de rede, Angular, etc.)
      if (error.error instanceof ErrorEvent) {
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