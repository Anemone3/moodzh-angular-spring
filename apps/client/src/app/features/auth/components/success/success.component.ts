import { Component, inject, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "@core/services/auth/auth.service";



@Component({selector:'auth-success',template:'<h1>Authentication por google</h1>'})
export class AuthSuccessComponent implements OnInit{
    
    activeRouter = inject(ActivatedRoute);
    router = inject(Router);
    private authService = inject(AuthService);

    ngOnInit(): void {
        this.activeRouter.queryParams.subscribe({
            next: (params)=>{
                const token = params['token'];
                console.log('token: ' +token)
                if(token){
                    // TODO: no se en que momento empezo a el token enviarse por servidor, usar transferState
                    this.authService.setToken(token);
                    this.router.navigate(['/'],{replaceUrl: true});
                }else{
                    this.router.navigate(['/auth/login'],{replaceUrl: false});
                }
            },
            error: (err) => {
                console.error("Provider ERROR: " + err);
                this.router.navigate(['/auth/login'],{replaceUrl:true});
            }
        })
    }

}