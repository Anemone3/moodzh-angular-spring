import { Component, HostBinding, inject, input, signal } from "@angular/core";
import { Router } from "@angular/router";


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
export class AvatarProfileComponent  {

  private router = inject(Router);

  private readonly _size:number = 80;
  protected menuList:AvatarProfileMenuItem[] = [
    { label: 'Profile', icon: 'user'},
    { label: 'Settings', icon: 'settings' },
    { label: 'Logout', icon: 'logout'  }
  ] ;
  avatarUrl = input<string>("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeCCGpYm2-Wa5cTBlEen4f4b9Qm-g8lsiVejL6CeSDhY3ZQwKe4vgv9lWfgJTH9Cd61vs&usqp=CAU");
  isMenuOpen = signal<boolean>(false);
  size = input<number>(this._size);
  directionMenu = input<'left' | 'right'>('left');

  public get lgSize(): string {
    return this.size() == this._size ? 'h-8 w-8' : 'h-6 w-6';
  }

  @HostBinding('style.height.px') get hostHeight() { return this.size(); }
  @HostBinding('style.width.px') get hostWidth() { return this.size(); }

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
