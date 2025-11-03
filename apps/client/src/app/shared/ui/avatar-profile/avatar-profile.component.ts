import { Component, effect, HostBinding, inject, input, signal } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "@core/services/auth/auth.service";


interface AvatarProfileMenuItem {
  label: string;
  icon: string;
}


@Component({
  selector: 'avatar-profile',
  templateUrl: './avatar-profile.html',
  imports:[],
  styles:[`:host { position: relative; display:block }`]
})
export class AvatarProfileComponent {

  private router = inject(Router);
  private authService = inject(AuthService);
  private readonly _size:number = 80;
  protected menuList:AvatarProfileMenuItem[] = [
    { label: 'Profile', icon: 'user'},
    { label: 'Settings', icon: 'settings' },
    { label: 'Logout', icon: 'logout'  }
  ] ;
  user = input.required<User|null>();
  isMenuOpen = signal<boolean>(false);
  size = input<number>(this._size);
  directionMenu = input<'left' | 'right'>('left');
  profile = signal<string>('/profile.webp');
  public get lgSize(): string {
    return this.size() == this._size ? 'h-8 w-8' : 'h-6 w-6';
  }

  @HostBinding('style.height.px') get hostHeight() { return this.size(); }
  @HostBinding('style.width.px') get hostWidth() { return this.size(); }


  constructor() {
    effect(()=>{
      const u = this.user();
      this.updateProfilePicture(u);
    })
  }
  

  private updateProfilePicture(user: User | null) {
  if (user) {
    if (user.profile) {
      this.profile.set(user.profile);
    } else {
      this.profile.set(
        `https://ui-avatars.com/api/?name=${user.username}&background=ddd&color=555&size=128`
      );
    }
  } else {
    this.profile.set('/profile.webp');
  }
}

  protected actionHandler(index: number): void {
    const itemSelected:AvatarProfileMenuItem = this.menuList[index]
    const labels = this.menuList.map(item => item.label);
    switch(itemSelected.label){
      case labels[0]:
        this.router.navigate(['/profile']);
        console.log('Profile');
        break;
      case labels[1]:
        this.router.navigate(['/settings']);
        console.log('Settings');
        break;
      case labels[2]:
        this.authService.logout();
        console.log('Logout');
        break;
      default:
        console.log('Unknown');
        break;
    }

    this.toggleMenu();
  }

  public toggleMenu(): void {
    this.isMenuOpen.update(current => !current);
  }
}
