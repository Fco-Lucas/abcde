import { Component } from '@angular/core';
import { ThemeToggleComponent } from '../../components/theme-toogle.component/theme-toogle.component';

@Component({
  selector: 'app-profile-page',
  imports: [
    ThemeToggleComponent
  ],
  templateUrl: './profile-page.component.html',
})
export class ProfilePageComponent {

}
