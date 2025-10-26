import { Component, input } from "@angular/core";



@Component({
  selector: 'collection-card',
  templateUrl: './collection-card.html',
  host: {
      'class': 'relative flex flex-col gap-2.5 pb-2'
    }
})
export class CollectionCard {

  imageCollections = input<string[]>([]);

}
