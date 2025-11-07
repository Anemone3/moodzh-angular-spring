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

    static markAllAsTouched(formGroup: FormGroup) {
        Object.values(formGroup.controls).forEach((control: AbstractControl) => {
            control.markAsTouched(); 

            if (control instanceof FormGroup) {
                this.markAllAsTouched(control);
            }
        });
    }
}