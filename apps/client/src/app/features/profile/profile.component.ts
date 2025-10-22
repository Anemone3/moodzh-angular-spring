import { Component } from '@angular/core';
import { RouterOutlet, RouterLinkActive, RouterLinkWithHref } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.html',
  imports: [RouterOutlet, RouterLinkActive, RouterLinkWithHref],
})
export class ProfileComponent {}
