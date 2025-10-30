import { isPlatformBrowser, UpperCasePipe } from '@angular/common';
import { Component, HostListener, Inject, input, OnInit, PLATFORM_ID, signal } from '@angular/core';
import { RouterLinkActive, RouterLinkWithHref } from '@angular/router';
import { AvatarProfileComponent } from '../avatar-profile/avatar-profile.component';

interface MenuItem {
  label: string;
  route: string;
}

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.html',
  imports: [RouterLinkActive, AvatarProfileComponent, UpperCasePipe, RouterLinkWithHref],
})
export class NavbarComponent implements OnInit {
  isAuthenticate = input.required<boolean>();
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
