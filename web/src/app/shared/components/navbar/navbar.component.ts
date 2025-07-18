import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import type { AuthenticatedUserRole } from '../../../core/services/auth.service';
import { MatMenuModule } from '@angular/material/menu';

// Interface para definir a estrutura de um item de navegação
export interface NavItem {
  label: string;
  link: string;
  roles: AuthenticatedUserRole[]
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, MatToolbarModule, MatButtonModule, MatIconModule, MatMenuModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  @Input() navItems: NavItem[] | null = []; // Recebe a lista de links para exibir
  @Output() logoutClicked = new EventEmitter<void>(); // Emite um evento de logout

  onLogout(): void {
    this.logoutClicked.emit();
  }
}