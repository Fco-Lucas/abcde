import { Component, inject } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-home-page',
  imports: [MatButtonModule],
  templateUrl: './home-page.component.html',
})
export class HomePageComponent {
  private authService = inject(AuthService);

  onLogout() {
    this.authService.logout();
  }
}
