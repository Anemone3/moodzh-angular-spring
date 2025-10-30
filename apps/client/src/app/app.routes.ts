import { Routes } from '@angular/router';
import { PROFILE_ROUTER } from './features/profile/profile.router';
import { AUTH_ROUTES } from '@features/auth/auth.router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./features/home/home.component').then((c) => c.HomeComponent),
  },
  {
    path: 'profile',
    loadComponent: () =>
      import('./features/profile/profile.component').then((c) => c.ProfileComponent),
    children: PROFILE_ROUTER,
  },
  {
    path: 'auth',
    loadComponent: () => import('./features/auth/auth.component').then((c) => c.AuthComponent),
    children: AUTH_ROUTES,
  },
];
