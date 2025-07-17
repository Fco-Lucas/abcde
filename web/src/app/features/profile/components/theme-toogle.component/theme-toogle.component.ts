import { Component } from '@angular/core';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { ThemeService } from '../../../../core/services/theme.service';

@Component({
  selector: 'app-theme-toggle',
  standalone: true,
  imports: [
    MatSlideToggleModule,
    MatIconModule
  ],
  templateUrl: './theme-toogle.component.html',
})
export class ThemeToggleComponent {
  // Injeta o serviço e expõe o signal diretamente ao template
  constructor(public themeService: ThemeService) {}

  onToggle(): void {
    this.themeService.toggleTheme();
  }
}