import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { RegisterPageComponent } from './features/auth/pages/register-page/register-page.component';
import { NotFoundPageComponent } from './core/pages/not-found-page/not-found-page.component';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { HomePageComponent } from './features/home/pages/home-page/home-page.component';
import { AppLayoutComponent } from './layout/app-layout/app-layout.component';
import { authGuard } from './core/guards/auth.guard';
import { publicGuard } from './core/guards/public.guard';
import { ClientsPageComponent } from './features/clients/pages/clients-page/clients-page.component';
import { roleGuard } from './core/guards/role.guard';
import { ClientUsersPageComponent } from './features/clientUsers/pages/client-users-page/client-users-page.component';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthLayoutComponent,
    canActivate: [publicGuard],
    children: [
      { path: 'login', component: LoginPageComponent, title: "ABCDE | Login" },
      { path: 'register', component: RegisterPageComponent, title: "ABCDE | Cadastro de Clientes" },
      { path: '', redirectTo: 'login', pathMatch: 'full' } 
    ]
  },
  {
    path: 'app',
    component: AppLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'home', component: HomePageComponent, title: "ABCDE | Início" },
      { path: 'clients', component: ClientsPageComponent, title: "ABCDE | Clientes", canActivate: [roleGuard], data: { roles: ['CLIENT'] } },
      { path: 'clientUsers/:id', component: ClientUsersPageComponent, title: 'ABCDE | Usuários do Cliente', canActivate: [roleGuard], data: { roles: ['CLIENT'] } },
    ]
  },
  { path: '', redirectTo: 'auth', pathMatch: 'full' }, 
  { path: "**", component: NotFoundPageComponent },
];
