import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-ui-error',
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './ui-error.component.html',
})
export class UiErrorComponent {
  @Input() message: string | null = null;
  @Input() title: string | null = "Ocorreu um erro";
}
