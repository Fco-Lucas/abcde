import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap, Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode'; // Usado para toda a lógica de token
import { environment } from '../../../environments/environment';
import type { AuthPayload, AuthResponse } from '../models/auth.model';

const AUTH_TOKEN_COOKIE_NAME = 'jwt_auth_token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);
  private router = inject(Router);
  private cookieService = inject(CookieService);

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(!this.isTokenExpired());
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  private storeToken(token: string): void {
    try {
      // 1. Usa jwt-decode para pegar a data de expiração.
      const decodedToken = jwtDecode(token);
      const expiresAt = new Date(decodedToken.exp! * 1000);

      this.cookieService.set(
        AUTH_TOKEN_COOKIE_NAME,
        token,
        { 
          expires: expiresAt,
          path: '/',
          secure: environment.production,
          sameSite: 'Lax'
        }
      );
      this.isAuthenticatedSubject.next(true);

    } catch (error) {
      console.error("Erro ao decodificar o token para armazenar no cookie:", error);
      this.isAuthenticatedSubject.next(false);
    }
  }

  public removeToken(): void {
    this.cookieService.delete(AUTH_TOKEN_COOKIE_NAME, '/');
    this.isAuthenticatedSubject.next(false);
  }

  public getToken(): string {
    return this.cookieService.get(AUTH_TOKEN_COOKIE_NAME);
  }

  public isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    try {
      const decodedToken = jwtDecode(token);
      const expirationDate = decodedToken.exp! * 1000;
      return expirationDate < Date.now();
    } catch (error) {
      console.error(`JWT TOKEN inválido ou expiraado:`, error);
      return true;
    }
  }

  login(credentials: AuthPayload): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}auth`, credentials).pipe(
      tap(response => {
        this.storeToken(response.token);
      })
    );
  }

  logout(): void {
    this.removeToken();
    this.router.navigate(['/auth/login']);
  }
}