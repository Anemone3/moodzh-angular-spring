import { isPlatformBrowser } from '@angular/common';
import { Component, HostListener, Inject, OnInit, PLATFORM_ID, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.html',
  imports: [RouterLink, RouterLinkActive],
})
export class NavbarComponent implements OnInit {
  isOpen = signal<boolean>(false);
  isLgScreen = signal<boolean>(false);

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
