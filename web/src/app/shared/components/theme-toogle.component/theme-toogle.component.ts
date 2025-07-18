import { Component } from '@angular/core';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { ThemeService } from '../../../core/services/theme.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-theme-toggle',
  standalone: true,
  imports: [
    MatButtonModule,
    MatSlideToggleModule,
    MatIconModule
  ],
  templateUrl: './theme-toogle.component.html',
  styleUrl: './theme-toogle.component.scss'
})
export class ThemeToggleComponent {
  // Injeta o serviço e expõe o signal diretamente ao template
  constructor(public themeService: ThemeService) {}

  onToggle(): void {
    this.themeService.toggleTheme();
  }
}