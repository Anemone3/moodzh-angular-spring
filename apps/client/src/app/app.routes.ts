import { Routes } from '@angular/router';
import { PROFILE_ROUTER } from './features/profile/profile.router';
import { AUTH_ROUTES } from '@features/auth/auth.router';
import { ProfileGuard } from '@core/guards/profile.guard';
import { ProfileResolver } from '@core/resolvers/profile.resolver';

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
    canActivate: [ProfileGuard]
  },
  {
    path: 'profile/:uid',
    loadComponent: () =>
      import('./features/profile/profile.component').then((c) => c.ProfileComponent),
    children: PROFILE_ROUTER,
    resolve:{user: ProfileResolver}
  },
  {
    path: 'auth',
    loadComponent: () => import('./features/auth/auth.component').then((c) => c.AuthComponent),
    children: AUTH_ROUTES,
  },
];
