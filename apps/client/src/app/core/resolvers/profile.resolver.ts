import { Injectable, inject } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { UserService } from '@core/services/user/user.service';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ProfileResolver implements Resolve<any> {
  private userService = inject(UserService);
  private router = inject(Router);

  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    const uid = route.paramMap.get('uid');
    if (!uid) return of(null);

    let user: User|null = null;

    if(uid == '123'){
        user = {
            id:'123',
            email: 'adad@gmail.com',
            username:'',
            linkSocial:{},
            location:{},
            providerId: '',
            profile:'',
            provider:'',
            createdAt:'',
            lastUpdated:''
        }
    }

    if(user == null){
        this.router.navigateByUrl('/auth/sign-in');

    }

    return of(user);
  }
}
