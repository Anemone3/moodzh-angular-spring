import { Directive, effect, inject, TemplateRef, ViewContainerRef } from "@angular/core";
import { AuthService } from "@core/services/auth/auth.service";




@Directive({
    selector: '[isAuthenticate]',
})
export class AuthenticateDirective{
    private authService = inject(AuthService);
    private viewContainer = inject(ViewContainerRef);
    private templateRef = inject(TemplateRef<unknown>);

    constructor(){
        effect(()=>{
            this.authService.isAuthenticated() ? this.viewContainer.createEmbeddedView(this.templateRef) : this.viewContainer.clear();
        })
    }
}