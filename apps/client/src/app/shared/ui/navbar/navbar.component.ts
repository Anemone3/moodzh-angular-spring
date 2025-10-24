import { isPlatformBrowser, UpperCasePipe } from '@angular/common';
import { Component, HostListener, Inject, OnInit, PLATFORM_ID, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AvatarProfileComponent } from '../avatar-profile/avatar-profile.component';

interface MenuItem {
  label: string;
  route: string;
}

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.html',
  imports: [RouterLink, RouterLinkActive,AvatarProfileComponent, UpperCasePipe],
})
export class NavbarComponent implements OnInit {
  isOpen = signal<boolean>(false);
  isLgScreen = signal<boolean>(false);
  menuItems: MenuItem[] = [
    { label: 'Home', route: '/' },
    { label: 'Collections', route: '/profile/collections' },
    { label: 'Categories', route: '/profile' },
  ];
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.isLgScreen.set(window.innerWidth >= 1024);
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    if (isPlatformBrowser(this.platformId)) {
      const isLg = window.innerWidth >= 1024;
      const wasLg = this.isLgScreen();
      this.isLgScreen.set(isLg);
      if (isLg && !wasLg) {
        this.isOpen.set(true);
      } else if (!isLg && wasLg) {
        this.isOpen.set(false);
      }
    }
  }

  onHandleNavbar() {
    this.isOpen.set(!this.isOpen());
  }
}
