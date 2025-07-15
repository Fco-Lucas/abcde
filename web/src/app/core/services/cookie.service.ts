import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CookieService {
  constructor() {}

  // Define um cookie
  // NOTA DE SEGURANÇA: Para tokens de autenticação, cookies HttpOnly configurados pelo backend são mais seguros.
  // Este cookie é acessível por JavaScript. Considere SameSite=Strict e Secure=true em produção.
  set(name: string, value: string, days?: number, path: string = '/'): void {
    if (typeof document === 'undefined') return;

    let expires = '';
    if (days) {
      const date = new Date();
      date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
      expires = `; expires=${date.toUTCString()}`;
    }
    // Adicionando SameSite=Lax por padrão. Para maior segurança, considere 'Strict'.
    // Adicionar 'Secure' se a aplicação for servida sobre HTTPS.
    const secureFlag = window.location.protocol === 'https:' ? '; Secure' : '';
    document.cookie = `${name}=${value || ''}${expires}; path=${path}; SameSite=Lax${secureFlag}`;
  }

  // Obtém um cookie
  get(name: string): string | null {
    if (typeof document === 'undefined') return null; // Guard clause

    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) === ' ') c = c.substring(1, c.length);
      if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
  }

  // Deleta um cookie
  delete(name: string, path: string = '/'): void {
    if (typeof document === 'undefined') return; // Guard clause

    // Para deletar, define-se uma data de expiração no passado e remove o valor.
    // Adicionando SameSite=Lax por padrão. Para maior segurança, considere 'Strict'.
    // Adicionar 'Secure' se a aplicação for servida sobre HTTPS.
    const secureFlag = window.location.protocol === 'https:' ? '; Secure' : '';
    document.cookie = `${name}=; Max-Age=-99999999; path=${path}; SameSite=Lax${secureFlag}`;
  }
}
