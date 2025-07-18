import { Component, inject } from '@angular/core';

import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../../core/services/auth.service';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home-page',
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule
  ],
  templateUrl: './home-page.component.html',
})
export class HomePageComponent {
  private authService = inject(AuthService);
}
