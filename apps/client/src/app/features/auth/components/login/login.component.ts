import { Component } from '@angular/core';
import { InputComponent } from '@shared/ui/input/input.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'auth-login',
  templateUrl: './login.html',
  host: {
    class: 'w-full block',
  },
  imports: [InputComponent, RouterLink],
})
export class LoginComponent {

  navigateGoogleCallback() {
    console.log('auth google')
    window.location.href = "http://localhost:8080/api/oauth2/authorization/google?redirect_uri=http://localhost:4200/auth/success";
  }
}
