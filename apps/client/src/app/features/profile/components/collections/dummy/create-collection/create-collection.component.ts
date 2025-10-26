import { DIALOG_DATA, DialogRef } from "@angular/cdk/dialog";
import { Component, inject } from "@angular/core";



@Component({
  selector:'create-collection',
  templateUrl: './create-collection.html',

})
export class CreateCollectionComponent {
  dialogRef = inject<DialogRef<string>>(DialogRef<string>);
  data = inject(DIALOG_DATA)

  closeModal() {
    this.dialogRef.close();
  }

  saveCollection(collection: any){
    this.dialogRef.close(collection);
  }
}
