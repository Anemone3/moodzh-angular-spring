import { Routes } from '@angular/router';
import { PROFILE_ROUTER } from './features/profile/profile.router';

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
];
