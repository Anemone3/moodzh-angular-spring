import { Location } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { InputComponent } from '@shared/ui/input/input.component';

@Component({
  selector: 'auth-register',
  templateUrl: './register.html',
  imports: [InputComponent],
})
export class RegisterComponent {

  location = inject(Location);
  profileUrl = signal<string>('');

  fb = inject(FormBuilder);

  form = this.fb.group({
    username: ['',[Validators.required,Validators.minLength(4)]],
    email: ['',[Validators.required,Validators.email]],
    profile: [this.profileUrl],
    password: ['',[Validators.required]],
    confirmPassword: ['',[Validators.required, (control: AbstractControl)=> {
        const password = control.get('password');
        const confirmPassword = control.get('confirmPassword');

        return password === confirmPassword ? null : {passwordNotMatch: true} //usar esta propiedad para exponerla en el html
    }]]
  })
 


  onSubmit(){

  }

  private registerAccount(register:RegisterRequest){

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
