import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";


export class CustomValidators {

    static strictEmail(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

            if (control.value && !emailRegex.test(control.value)) {
                return { strictEmail: true };
            }
            return null;
        };
    }

  static validMatchPassword(matchToControlName: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      // control padre
      const group = control.parent;
      if (!group) {
        return null;
      }

      // control que se envia, en este caso password
      const matchToControl = group.get(matchToControlName);
      
      // Si aun no se envia, o el valor actual es nulo, no validamos aun
      if (!matchToControl || !control.value) {
        return null; 
      }
      
      // comparamos, si no coindice tiramos el error con propiedad passwordMismatch
      if (control.value !== matchToControl.value) {
        return { passwordMismatch: true };
      }

      // Validamos si el error existe, y forzamos validar de nuevo para quitar el error si ya cumple
      if (group.hasError('passwordNotMatch')) {
          group.updateValueAndValidity(); // forzar re-validación del grupo
      }
      
      return null;
    };
  }

    static markAllAsTouched(formGroup: FormGroup) {
        Object.values(formGroup.controls).forEach((control: AbstractControl) => {
            control.markAsTouched(); 

            if (control instanceof FormGroup) {
                this.markAllAsTouched(control);
            }
        });
    }
}