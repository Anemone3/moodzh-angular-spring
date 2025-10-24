import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from '@shared/ui/navbar/navbar.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.html',
})
export class App implements OnInit {
  private router = inject(Router);
  private hiddenNavbarRoutes: string[] = ['^/profile(/.*)?$', '^/settings(/.*)?$'];
  private currentRoute = signal<string>(this.router.url);
  showNavbar = computed(() => {
    return !this.hiddenNavbarRoutes.some(route=> this.currentRoute().match(route));
  });
  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentRoute.set(event.urlAfterRedirects);
        console.log('current router ' + event.urlAfterRedirects);
      }
    });
  }
}
