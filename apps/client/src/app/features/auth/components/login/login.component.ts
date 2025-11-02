import { Component, inject } from '@angular/core';
import { InputComponent } from '@shared/ui/input/input.component';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'auth-login',
  templateUrl: './login.html',
  host: {
    class: 'w-full block',
  },
  imports: [InputComponent, RouterLink,ReactiveFormsModule],
})
export class LoginComponent {

  router = inject(Router);
  fb = inject(FormBuilder);

  /*TODO: Agregar validacion de email con regex */
  form = this.fb.group({
    email: ['', [Validators.email,Validators.required]], 
    password: ['',[Validators.required,Validators.min(6)]]
  });

  onSubmit() {
    console.log('submit')
    console.log(this.form);
  }

  navigateGoogleCallback() {
    console.log('auth google')
    window.location.href = "http://localhost:8080/api/oauth2/authorization/google?redirect_uri=http://localhost:4200/auth/success";
  }

  navigateToHome() {
    this.router.navigate(["/"]);
  }
}
