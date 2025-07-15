import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { RegisterPageComponent } from './features/auth/pages/register-page/register-page.component';
import { NotFoundPageComponent } from './core/pages/not-found-page/not-found-page.component';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { HomePageComponent } from './features/home/pages/home-page/home-page.component';
import { AppLayoutComponent } from './layout/app-layout/app-layout.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      { path: 'login', component: LoginPageComponent },
      { path: 'register', component: RegisterPageComponent },
      { path: '', redirectTo: 'login', pathMatch: 'full' } 
    ]
  },
  {
    path: 'app',
    component: AppLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'home', component: HomePageComponent },
    ]
  },
  { path: '', redirectTo: 'auth', pathMatch: 'full' }, 
  { path: "**", component: NotFoundPageComponent },
];
