import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap, Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode'; // Usado para toda a lógica de token
import { environment } from '../../../environments/environment';
import type { AuthPayload, AuthResponse } from '../models/auth.model';

const AUTH_TOKEN_COOKIE_NAME = 'jwt_auth_token';

export type AuthenticatedUserRole = 'COMPUTEX' | 'CLIENT' | 'CLIENT_USER' | null;

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

  // BehaviorSubject para o id do usuário autenticado
  private currentUserIdSubject = new BehaviorSubject<string | null>(null);
  public currentUserId$ = this.currentUserIdSubject.asObservable();

  constructor() {
    this.loadAuthenticatedUserRoleFromToken();
  }

  private loadAuthenticatedUserRoleFromToken(): void {
    const token = this.getToken();
    if (token && !this.isTokenExpired()) {
      const decodedToken = this.getDecodedToken();
      if (!decodedToken) {
        this.removeToken();
        return;
      }
      
      this.isAuthenticatedSubject.next(true);
      this.currentUserRoleSubject.next(decodedToken.role);
      this.currentUserIdSubject.next(decodedToken.id);
    } else {
      this.removeToken();
    }
  }

  public getDecodedToken(): TokenPayload | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      return jwtDecode<TokenPayload>(token);
    } catch (error) {
      console.error("Erro ao decodificar o token:", error);
      return null;
    }
  }

  private storeToken(token: string): void {
    // 1. Usa jwt-decode para pegar a data de expiração.
    const decodedToken = jwtDecode<TokenPayload>(token);
    if (!decodedToken) {
      this.removeToken();
      return;
    }
    const expiresAt = new Date(decodedToken.exp! * 1000);

    this.cookieService.set(
      AUTH_TOKEN_COOKIE_NAME,
      token,
      { 
        expires: expiresAt,
        path: '/',
        // secure: environment.production,
        secure: false,
        sameSite: 'Lax'
      }
    );
    this.isAuthenticatedSubject.next(true);
    this.currentUserRoleSubject.next(decodedToken.role);
    this.currentUserIdSubject.next(decodedToken.id);
  }

  public removeToken(): void {
    this.cookieService.delete(AUTH_TOKEN_COOKIE_NAME, '/');
    this.isAuthenticatedSubject.next(false);
    this.currentUserRoleSubject.next(null);
    this.currentUserIdSubject.next(null);
  }

  public getToken(): string {
    return this.cookieService.get(AUTH_TOKEN_COOKIE_NAME);
  }

  public isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    const decodedToken = jwtDecode<TokenPayload>(token);
    if (!decodedToken) {
      this.removeToken();
      return true; // Retorna true simulando com se tivesse expirado
    }
    const expirationDate = decodedToken.exp! * 1000;
    return expirationDate < Date.now();
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