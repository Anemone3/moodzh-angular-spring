import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "@core/services/auth/auth.service";


export const privateAuthGuard:CanActivateFn = (route:ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authService = inject(AuthService);
    return !authService.isAuthenticated();
}