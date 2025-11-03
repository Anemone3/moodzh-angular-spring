import { isPlatformBrowser } from '@angular/common';
import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';

@Component({ selector: 'auth-success', template: '<h1>Authentication por google</h1>' })
export class AuthSuccessComponent implements OnInit {
  activeRouter = inject(ActivatedRoute);
  router = inject(Router);
  platformId = inject(PLATFORM_ID);
  private authService = inject(AuthService);

  ngOnInit(): void {
    this.activeRouter.queryParams.subscribe({
      next: (params) => {
        const token = params['token'];
        if (isPlatformBrowser(this.platformId) && token) {
          console.log('token: ' + token);
          if (token) {
            this.authService.setToken(token);
            if (this.authService.accessToken()) {
              this.router.navigate(['/'], { replaceUrl: true });
            }
          } else {
            this.router.navigate(['/auth/login'], { replaceUrl: false });
          }
        }
      },
      error: (err) => {
        console.error('Provider ERROR: ' + err);
        this.router.navigate(['/auth/login'], { replaceUrl: true });
      },
    });
  }
}
