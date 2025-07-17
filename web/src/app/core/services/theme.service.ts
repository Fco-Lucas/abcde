import { DOCUMENT } from '@angular/common';
import { Inject, Injectable, Renderer2, RendererFactory2, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private renderer: Renderer2;
  // A classe que definimos no nosso SCSS
  private readonly darkThemeClass = 'dark-theme';

  isDarkTheme = signal<boolean>(false);

  constructor(
    @Inject(DOCUMENT) private document: Document,
    rendererFactory: RendererFactory2
  ) {
    this.renderer = rendererFactory.createRenderer(null, null);
    this.initializeTheme();
  }

  private initializeTheme(): void {
    const storedPreference = localStorage.getItem('isDarkTheme');
    if (storedPreference) {
      this.updateTheme(storedPreference === 'true');
    } else {
      const prefersDark = window.matchMedia?.('(prefers-color-scheme: dark)').matches;
      this.updateTheme(prefersDark);
    }
  }

  toggleTheme(): void {
    this.updateTheme(!this.isDarkTheme());
  }

  private updateTheme(isDark: boolean): void {
    this.isDarkTheme.set(isDark);
    localStorage.setItem('isDarkTheme', String(isDark));
    if (isDark) {
      this.renderer.addClass(this.document.body, this.darkThemeClass);
    } else {
      this.renderer.removeClass(this.document.body, this.darkThemeClass);
    }
  }
}