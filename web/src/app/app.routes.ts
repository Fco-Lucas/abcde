import { Routes } from '@angular/router';
import { NotFoundPageComponent } from './core/pages/not-found-page/not-found-page.component';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { HomePageComponent } from './features/home/pages/home-page/home-page.component';
import { AppLayoutComponent } from './layout/app-layout/app-layout.component';
import { authGuard } from './core/guards/auth.guard';
import { publicGuard } from './core/guards/public.guard';
import { LotDetailsPageComponent } from './features/home/pages/lot-details-page/lot-details-page.component';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthLayoutComponent,
    canActivate: [publicGuard],
    loadChildren: () => import('./features/auth/auth.routes')
  },
  {
    path: 'app',
    component: AppLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'home', component: HomePageComponent, title: "ABCDE | Início" },
      { path: 'loteDetails', component: LotDetailsPageComponent, title: "ABCDE | Informações do lote" },
      { path: 'profile', loadChildren: () => import('./features/profile/profile.routes') },
      { path: 'clients', loadChildren: () => import('./features/clients/clients.routes') }
    ]
  },
  { path: '', redirectTo: 'auth', pathMatch: 'full' }, 
  { path: "**", component: NotFoundPageComponent },
];
