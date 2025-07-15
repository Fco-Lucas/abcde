import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatButtonModule],
  template: '<router-outlet></router-outlet>',
})
export class App {
  protected readonly title = signal('web');
}
