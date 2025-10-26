import { Dialog, DialogConfig, DialogRef } from "@angular/cdk/dialog";
import { ComponentType } from "@angular/cdk/portal";
import { inject, Injectable } from "@angular/core";



@Injectable({
  providedIn: 'root'
})
export class ModalService {
  dialog = inject(Dialog)

  openDialog<T>(component:ComponentType<unknown>,data:T,config?:DialogConfig<unknown, DialogRef<unknown, unknown>>){
    const defaultConfig: DialogConfig<unknown, DialogRef<unknown, unknown>> = {
      width: '500px',
      height: '300px',
      data: data
    }
    const dialogRef = this.dialog.open(component,{
      ...defaultConfig,
      ...config,
    });

    return dialogRef;
  }
}
