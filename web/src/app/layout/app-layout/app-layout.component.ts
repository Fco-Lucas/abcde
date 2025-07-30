import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common'; // Importe o AsyncPipe
import { RouterOutlet } from '@angular/router';
import { combineLatest, map, Observable } from 'rxjs';
import { NavbarComponent, NavItem } from '../../shared/components/navbar/navbar.component';
import { AuthService, AuthenticatedUserRole } from '../../core/services/auth.service';

@Component({
  selector: 'app-app-layout',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, AsyncPipe], // Adicione NavbarComponent e AsyncPipe
  templateUrl: './app-layout.component.html',
})
export class AppLayoutComponent {
  private authService = inject(AuthService);
  
  // Cria um Observable que transforma o cargo do usuário na lista de links apropriada
  public navItems$: Observable<NavItem[]> = combineLatest([
    this.authService.currentUserRole$,
    this.authService.currentUserId$
  ]).pipe(
    map(([role, id]) => this.getNavItemsForRole(role, id))
  );

  private getNavItemsForRole(role: AuthenticatedUserRole, id: string | null): NavItem[] {
    const allItems: NavItem[] = [
      { label: 'Inicio', link: '/app/home', roles: ['COMPUTEX', 'CLIENT', 'CLIENT_USER'] },
      { label: 'Clientes', link: '/app/clients', roles: ['COMPUTEX'] },
      { label: 'Meus Usuários', link: `/app/clients/${id}/users`, roles: ['CLIENT'] },
      { label: 'Meu Perfil', link: '/app/profile', roles: ['COMPUTEX', 'CLIENT', 'CLIENT_USER'] },
      { label: 'Auditoria', link: '/app/auditLog', roles: ['COMPUTEX'] },
    ];
    
    return allItems.filter(item => item.roles.includes(role));
  }

  onLogout(): void {
    this.authService.logout();
  }
}