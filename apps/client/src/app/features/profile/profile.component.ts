import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet, RouterLinkActive, RouterLinkWithHref, ActivatedRoute } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';
import { AvatarProfileComponent } from '@shared/ui/avatar-profile/avatar-profile.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.html',
  imports: [RouterOutlet, RouterLinkActive, RouterLinkWithHref,AvatarProfileComponent],
})
export class ProfileComponent implements OnInit{

  activeRouter = inject(ActivatedRoute);
  private authService = inject(AuthService);

  uid: string | null = null;
  user:User| null = null;
  isAuthenticateAndSameUser = false;

  ngOnInit(): void {
      const uid = this.activeRouter.snapshot.paramMap.get('uid');
     console.log('uuid de user: ' + uid)
    if(uid){
      /* obtendria el user del uid */
      this.uid = uid;
       this.activeRouter.data.subscribe(data=>{
      console.log(data);
      this.user = data['user'];
      })

    } else {
      this.user = this.authService.user();
    }
   
  }


}
