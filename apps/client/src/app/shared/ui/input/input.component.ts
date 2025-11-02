import { ChangeDetectionStrategy, Component, computed, input, model, signal } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

/*   [class.error]="error()"
    [class.success]="!error()" */

@Component({
  selector: 'input-form',
  template: `<input
    type="text"
    [value]="value()"
    [id]="id()"
    [autocomplete]="autocomplete()"
    [disabled]="isDisabled()"
    [placeholder]="placeholder()"
    (blur)="onTouchedFn()"
    (input)="onChange($event)"
    [class]="heightStyle()"
    class="bg-none font-roboto-mono focus:ring-1 tracking-wide focus:ring-button border text-[1rem] border-border placeholder:text-text-placeholder rounded-md min-w-10 px-3 color-[#eeeef0] outline-0 relative  w-full"
  />`,
  host: {
    class: 'block w-full relative',
  },
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: InputComponent,
      multi: true,
    },
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InputComponent implements ControlValueAccessor {
  onChangeFn: any = () => {};
  onTouchedFn: any = () => {};

  readonly placeholder = input<string>('');

  readonly disabled = input<boolean>(false);
  readonly formDisabled = signal<boolean>(false);
  readonly isDisabled = computed(() => this.disabled() || this.formDisabled());

  readonly value = model<string>('');

  readonly id = input<string>('');
  heightStyle = input<string>('h-10');
  readonly autocomplete = input<string>('off');
  readonly error = signal<boolean>(false); // customError

  onChange(event: Event) {
    //console.log('input changed: ', event);
    const inputElement = event.target as HTMLInputElement;
    this.value.set(inputElement.value);
    this.onChangeFn(inputElement.value);
    //this.onTouchedFn();
    this.error.set(inputElement.value.trim() == '');
  }

  writeValue(obj: any): void {
    console.log('writeValue called with:', obj);
    this.value.set(obj);
  }
  registerOnChange(fn: any): void {
    this.onChangeFn = fn;
  }
  registerOnTouched(fn: any): void {
    this.onTouchedFn = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.formDisabled.set(isDisabled);
  }
}
