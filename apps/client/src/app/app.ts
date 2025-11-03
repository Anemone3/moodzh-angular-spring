import { Component, computed, effect, inject, OnInit, signal } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';
import { NavbarComponent } from '@shared/ui/navbar/navbar.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.html',
})
export class App implements OnInit {
  private authService = inject(AuthService);

  isAuth = this.authService.isAuthenticated;
  user = this.authService.user;

  private router = inject(Router);
  private hiddenNavbarRoutes: string[] = ['^/auth(/.*)?$', '^/settings(/.*)?$'];
  private currentRoute = signal<string>(this.router.url);
  showNavbar = computed(() => {
    return !this.hiddenNavbarRoutes.some((route) => this.currentRoute().match(route));
  });

  constructor() {
    effect(() => {
      const token = this.authService.accessToken();
      const user = this.authService.user();

      if (token && !user) {
        this.authService.init();
      }
    });
  }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentRoute.set(event.urlAfterRedirects);
        console.log('current router ' + event.urlAfterRedirects);
      }
    });

    console.log(this.authService.user());
  }
}
