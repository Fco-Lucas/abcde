import { Routes } from '@angular/router';
import { LoginPage } from './features/auth/pages/login-page/login-page.component';
import { RegisterPage } from './features/auth/pages/register-page/register-page';
import { NotFoundPageComponent } from './core/pages/not-found-page/not-found-page.component';

export const routes: Routes = [
  { path: 'login', component: LoginPage },
  { path: 'register', component: RegisterPage },

  { path: '', redirectTo: 'login', pathMatch: 'full' }, 
  { path: "**", component: NotFoundPageComponent },
];
