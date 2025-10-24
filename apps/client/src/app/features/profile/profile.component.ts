import { Component } from '@angular/core';
import { RouterOutlet, RouterLinkActive, RouterLinkWithHref } from '@angular/router';
import { AvatarProfileComponent } from '@shared/ui/avatar-profile/avatar-profile.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.html',
  imports: [RouterOutlet, RouterLinkActive, RouterLinkWithHref,AvatarProfileComponent],
})
export class ProfileComponent {}
