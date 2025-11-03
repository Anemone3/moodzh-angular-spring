import { inject, Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';

@Injectable({ providedIn: 'root' })
export class ProfileGuard implements CanActivate {
  router = inject(Router);
  private authService = inject(AuthService);
  
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    const isAuth = this.authService.isAuthenticated();

    if (!isAuth) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
