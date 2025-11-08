import { Location } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '@core/services/auth/auth.service';
import { InputComponent } from '@shared/ui/input/input.component';
import { CustomValidators } from '@shared/utils/CustomValidators';
import { finalize, switchMap } from 'rxjs';

@Component({
  selector: 'auth-register',
  templateUrl: './register.html',
  imports: [InputComponent,ReactiveFormsModule],
})
export class RegisterComponent {

  location = inject(Location);
  private readonly authService = inject(AuthService);
  profileUrl = signal<string>('');
  isLoading = this.authService._loading.asReadonly();
  fb = inject(FormBuilder);

  form = this.fb.group({
    username: ['',[Validators.required,Validators.minLength(4)]],
    email: ['',[Validators.required,CustomValidators.strictEmail()]],
    profile: [this.profileUrl],
    password: ['',[Validators.required,Validators.minLength(6)]],
    confirmPassword: ['',[Validators.required, CustomValidators.validMatchPassword('password')]]
  })
 


  onSubmit(){
    console.log(this.form)
    if(this.form.invalid){
      CustomValidators.markAllAsTouched(this.form);
      return;
    }


    const {email,password,profile,username} = this.form.value;
    this.authService._loading.set(true);
    if(!email || !password || !profile || !username) return; // ya tiene valores pero para asegurar no enviar nulos

    this.authService.register({email: email,password: password,profile_url: this.profileUrl() || null, username: username}).pipe(
      switchMap(()=> this.authService.fetchUser()),
      finalize(()=> this.authService._loading.set(false))
    ).subscribe({
      next: (data) =>{
        console.log('register')
        /* TODO: mas adelante podemos mostrar un toast para enviar un correo de verificacion */
        console.log(data)
      },
      error: (err) =>{
        console.error("bad request en register");
        console.log(err)
      }
    })
    
  }



  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const fileInput = input.files;

    if (!fileInput || fileInput.length == 0) return;
    
    let file = fileInput[0];

    if (!file.type.startsWith('image/')) return;

    const maxSize = 2 * 1024 * 1024; // 2MB
    if (file.size > maxSize) {
      console.log('la imagen superá el tamaño');
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      this.profileUrl.set(reader.result as string);
    }
    reader.readAsDataURL(file);
  }

  onClearImage() {
    this.profileUrl.set('');
  }


  returnToPreviousRoute() {
    this.location.back();
  }
}
