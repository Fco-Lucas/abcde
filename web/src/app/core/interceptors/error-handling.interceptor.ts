import { HttpInterceptorFn, type HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorHandlingInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Ocorreu um erro desconhecido.';
      
      // Lógica para formatar a mensagem de erro (a mesma que você já tinha)
      if (error.error instanceof ErrorEvent) {
        errorMessage = `Erro no cliente: ${error.error.message}`;
      } else {
        errorMessage = `Erro no servidor (código ${error.status}): ${error.message}`;
      }
      
      console.error('Erro capturado pelo interceptor:', errorMessage, error);
      return throwError(() => new Error(errorMessage));
    })
  );
};
