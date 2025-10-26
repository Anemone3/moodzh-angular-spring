import { Component, inject } from '@angular/core';
import { CollectionCard } from './dummy/collection-card/collection-card.component';
import { ModalService } from '@core/services/dialog-cdk/modal.service';
import { CreateCollectionComponent } from './dummy/create-collection/create-collection.component';

@Component({
  selector: 'profile-collections',
  templateUrl: './collections.html',
  imports:[CollectionCard],
  styles: [`
    .modal-collection {
      transform: translate(-50%, calc(-50% - 90px)) !important;
    }
  `]
})
export class CollectionsProfileComponent {

  readonly modalService = inject(ModalService);

  list = Array.from({ length: 2 }, (_, i) => i + 1);
  imagesMock: string[] = [];

  openModal(){
    const dialogRef= this.modalService.openDialog(CreateCollectionComponent,{},{hasBackdrop: true, panelClass: 'modal-collection'});
    dialogRef.closed.subscribe(console.log);
  }

}
