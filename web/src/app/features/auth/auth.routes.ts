import type { Routes } from "@angular/router";
import { LoginPageComponent } from "./pages/login-page/login-page.component";
import { RegisterPageComponent } from "./pages/register-page/register-page.component";

const AUTH_ROUTES: Routes = [
  { 
    path: 'login', 
    component: LoginPageComponent, 
    title: "Login | ABCDE" 
  },
  { 
    path: 'register',
    component: RegisterPageComponent,
    title: "Cadastro de Clientes | ABCDE"
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' } 
];

export default AUTH_ROUTES;