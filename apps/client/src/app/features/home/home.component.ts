import { Component } from '@angular/core';
import { AuthenticateDirective } from '@core/directives/authenticate.directive';
import { CardImagesComponent } from "@shared/ui/card-images/card-images.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.html',
  imports: [AuthenticateDirective, CardImagesComponent]
})
export class HomeComponent {}
