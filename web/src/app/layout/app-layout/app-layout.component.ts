import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common'; // Importe o AsyncPipe
import { RouterOutlet } from '@angular/router';
import { map, Observable } from 'rxjs';
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
  public navItems$: Observable<NavItem[]> = this.authService.currentUserRole$.pipe(
    map(role => this.getNavItemsForRole(role))
  );

  private getNavItemsForRole(role: AuthenticatedUserRole): NavItem[] {
    const allItems: NavItem[] = [
      { label: 'Inicio', link: '/app/home' },
      { label: 'Clientes', link: '/app/clients' },
      { label: 'Meu Perfil', link: '/app/profile' },
    ];
    
    if (role === 'CLIENT') {
      return allItems;
    }
    if (role === 'CLIENT_USER') {
      return allItems.filter(item => item.label === 'Home' || item.label === 'Meu Perfil');
    }
    // Se não tiver cargo ou for anônimo, não vê nada
    return [];
  }

  onLogout(): void {
    this.authService.logout();
  }
}