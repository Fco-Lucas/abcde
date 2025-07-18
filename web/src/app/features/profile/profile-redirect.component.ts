import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../../core/services/auth.service";
import { toSignal } from "@angular/core/rxjs-interop";

@Component({
  standalone: true,
  template: '',
})
export class ProfileRedirectComponent {
  constructor(private router: Router, private authService: AuthService) {
    const roleBreviator = toSignal(this.authService.currentUserRole$);
    const role = roleBreviator();

    if (role === 'CLIENT_USER') {
      this.router.navigate(['app/profile/user']);
    } else if(role === 'CLIENT' || role === 'COMPUTEX') {
      this.router.navigate(['app/profile/client']);
    }
  }
}
