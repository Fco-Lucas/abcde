import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap, Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode'; // Usado para toda a lógica de token
import { environment } from '../../../environments/environment';
import type { AuthPayload, AuthResponse } from '../models/auth.model';

const AUTH_TOKEN_COOKIE_NAME = 'jwt_auth_token';

export type AuthenticatedUserRole = 'CLIENT' | 'CLIENT_USER' | null;

interface TokenPayload {
  sub: string;
  iat: number;
  exp: number;
  id: string;
  role: AuthenticatedUserRole,
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);
  private router = inject(Router);
  private cookieService = inject(CookieService);

  // Behavior para autenticação (usuário logado ou não)
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(!this.isTokenExpired());
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  // BehaviorSubject para o cargo
  private currentUserRoleSubject = new BehaviorSubject<AuthenticatedUserRole>(null);
  public currentUserRole$ = this.currentUserRoleSubject.asObservable();

  constructor() {
    this.loadAuthenticatedUserRoleFromToken();
  }

  private loadAuthenticatedUserRoleFromToken(): void {
    const token = this.getToken();
    if (token && !this.isTokenExpired()) {
      try {
        const decodedToken = jwtDecode<TokenPayload>(token);
        this.currentUserRoleSubject.next(decodedToken.role);
      } catch (error) {
        this.currentUserRoleSubject.next(null);
      }
    } else {
      this.currentUserRoleSubject.next(null);
    }
  }

  private storeToken(token: string): void {
    try {
      // 1. Usa jwt-decode para pegar a data de expiração.
      const decodedToken = jwtDecode<TokenPayload>(token);
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
      this.currentUserRoleSubject.next(decodedToken.role);
    } catch (error) {
      console.error("Erro ao decodificar o token para armazenar no cookie:", error);
      this.logout();
    }
  }

  public removeToken(): void {
    this.cookieService.delete(AUTH_TOKEN_COOKIE_NAME, '/');
    this.isAuthenticatedSubject.next(false);
    this.currentUserRoleSubject.next(null);
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