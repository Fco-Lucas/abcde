import type { Routes } from "@angular/router";
import { ProfileClientPageComponent } from "./pages/profile-client-page/profile-client-page.component";
import { ProfileUserPageComponent } from "./pages/profile-user-page/profile-user-page.component";
import { roleGuard } from "../../core/guards/role.guard";
import { ProfileRedirectComponent } from "./profile-redirect.component";
import { profileGuard } from "../../core/guards/profile.guard";

const PROFILE_ROUTES: Routes = [
  {
    path: '',
    canActivate: [profileGuard],
    component: ProfileRedirectComponent
  },
  {
    path: 'client',
    component: ProfileClientPageComponent,
    canActivate: [roleGuard],
    data: { roles: ['COMPUTEX', 'CLIENT'] },
    title: 'Perfil do cliente | ABCDE'
  },
  {
    path: 'user', 
    component: ProfileUserPageComponent,
    canActivate: [roleGuard],
    data: { roles: ['COMPUTEX', 'CLIENT_USER'] },
    title: 'Perfil do usuário | ABCDE'
  }
];

export default PROFILE_ROUTES;