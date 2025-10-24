import { Component, HostBinding, input, signal } from "@angular/core";


@Component({
  selector: 'avatar-profile',
  templateUrl: './avatar-profile.html',
  styles:[`:host { position: relative; display:block }`]
})
export class AvatarProfileComponent  {

  private readonly _size:number = 80;

  avatarUrl = input<string>("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeCCGpYm2-Wa5cTBlEen4f4b9Qm-g8lsiVejL6CeSDhY3ZQwKe4vgv9lWfgJTH9Cd61vs&usqp=CAU");
  isMenuOpen = signal<boolean>(false);
  size = input<number>(this._size);

  public get lgSize(): string {
    return this.size() == this._size ? 'h-8 w-8' : 'h-6 w-6';
  }

  @HostBinding('style.height.px') get hostHeight() { return this.size(); }
  @HostBinding('style.width.px') get hostWidth() { return this.size(); }



  public toggleMenu(): void {
    this.isMenuOpen.update(current => !current);
  }
}
