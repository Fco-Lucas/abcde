import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-ui-not-found',
  imports: [
    MatIconModule
  ],
  templateUrl: './ui-not-found.component.html',
})
export class UiNotFoundComponent {
  @Input() title!: string;
  @Input() subtitle!: string;
}
