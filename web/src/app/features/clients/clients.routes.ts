import { Routes } from '@angular/router';
import { ClientsPageComponent } from './pages/clients-page/clients-page.component';
import { roleGuard } from '../../core/guards/role.guard';
import { ClientUsersPageComponent } from './pages/client-users-page/client-users-page.component';

const CLIENTS_ROUTES: Routes = [
  {
    path: '',
    component: ClientsPageComponent,
    canActivate: [roleGuard],
    data: { roles: ['COMPUTEX'] },
    title: 'Clientes | ABCDE'
  },
  {
    path: ':id/users', 
    component: ClientUsersPageComponent,
    canActivate: [roleGuard],
    data: { roles: ['COMPUTEX', 'CLIENT'] },
    title: 'Usuários do Cliente | ABCDE'
  }
];

export default CLIENTS_ROUTES;