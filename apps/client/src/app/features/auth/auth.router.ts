import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthSuccessComponent } from './components/success/success.component';

export const AUTH_ROUTES: Routes = [
  {
    path: 'sign-in',
    component: LoginComponent,
  },
  {
    path: 'sign-up',
    component: RegisterComponent,
  },
  {
    path: 'success',
    component: AuthSuccessComponent
  },
  {
    path: '',
    redirectTo: 'sign-in',
    pathMatch: 'full',
  },
];
