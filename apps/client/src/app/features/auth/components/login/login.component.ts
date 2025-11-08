import { Component, inject } from '@angular/core';
import { InputComponent } from '@shared/ui/input/input.component';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CustomValidators } from '@shared/utils/CustomValidators';
import { AuthService } from '@core/services/auth/auth.service';
import { finalize, switchMap } from 'rxjs';

@Component({
  selector: 'auth-login',
  templateUrl: './login.html',
  host: {
    class: 'w-full block',
  },
  imports: [InputComponent, RouterLink, ReactiveFormsModule],
})
export class LoginComponent {

  router = inject(Router);
  private readonly authService = inject(AuthService);
  fb = inject(FormBuilder);

  isLoading = this.authService._loading.asReadonly();

  /*TODO: Agregar validacion de email con regex */
  form = this.fb.group({
    email: ['', [Validators.email, Validators.required, CustomValidators.strictEmail()]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  onSubmit() {
    if (this.form.invalid) {
      CustomValidators.markAllAsTouched(this.form);
      return;
    }
    const { email, password } = this.form.value;
    if (!email || !password) return; // ya pasando las validaciones tiene valor, pero para prevenir enviar nulos al backend
    
    this.authService._loading.set(true);
    
    this.authService.login({ email: email, password: password })
      .pipe(
        switchMap(()=>this.authService.fetchUser()),
        finalize(()=> this.authService._loading.set(false))
      )
      .subscribe({
      next: () => {
        console.log('login')
        this.router.navigateByUrl("/");
      },
      error: (err) => {
        console.log(err)
        console.error("error login")
      }
    });
  }

  navigateGoogleCallback() {
    console.log('auth google')
    window.location.href = "http://localhost:8080/api/oauth2/authorization/google?redirect_uri=http://localhost:4200/auth/success";
  }

  navigateToHome() {
    this.router.navigate(["/"]);
  }
}
