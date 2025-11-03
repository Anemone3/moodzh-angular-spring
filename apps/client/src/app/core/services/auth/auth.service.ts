import { isPlatformBrowser} from "@angular/common";
import { HttpClient } from "@angular/common/http";
import { computed, inject, Injectable,  PLATFORM_ID, Signal, signal, TransferState } from "@angular/core";
import { API_URL } from "@configs/tokens";
import { Observable,tap } from "rxjs";


@Injectable({providedIn:'root'})
export class AuthService{
    
    private _user = signal<User | null>(null);    
    private _accessToken = signal<string|null>(null);

    private apiUrl = inject(API_URL);
    private http = inject(HttpClient)
    

    public user: Signal<User | null> = this._user.asReadonly();
    public accessToken: Signal<string | null> = this._accessToken.asReadonly();
    public isAuthenticated = computed(() => !!this._user());

    
    private transferState = inject(TransferState);
    private platformId = inject(PLATFORM_ID);


    constructor() {
        this.init();
    }

    init(){
        if(isPlatformBrowser(this.platformId)){
            this.fetchUser().subscribe({
                next: (user) =>{
                    console.log('login with user: ',user)
                    this._user.set(user) 
                }
            })
        }
    }

    login({ email, password }: LoginRequest): Observable<AccessToken>{
        return this.http.post<AccessToken>(this.apiUrl + "/auth/login", { email, password }, { withCredentials: true }).pipe(
            tap(response => this._accessToken.set(response.access_token))
        );
    }


    register({ email, password, profile_url, username, verify_password }: RegisterRequest): Observable<AccessToken>{
        
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            throw new Error("invalid email")
        }

        if (password.length !== verify_password.length || password != verify_password) {
            throw new Error('invalid password');
        }

        /* verify email, password, image if exists */
        return this.http.post<AccessToken>(this.apiUrl + "/auth/register", {
            email,
            password,
            profile_url,
            username,
        },{withCredentials: true}).pipe(tap(response=> this._accessToken.set(response.access_token)));
    }


    fetchUser(): Observable<User> {
    console.log('fetch user with token ' + this.accessToken());

    return this.http.get<User>(this.apiUrl + '/auth/me', {
        withCredentials: true,
        headers: {
        'Authorization': `Bearer ${this.accessToken()}`
        }
    });
    /*.pipe(
        tap(user => {
        if (isPlatformServer(this.platformId)) {
            this.transferState.set(USER_KEY, user);
            if (this._accessToken()) {
            this.transferState.set(ACCESS_TOKEN_KEY, this._accessToken()!);
            }
        }
        })
    );*/
    }


    setToken(token:string){
        if(!token) return;
        this._accessToken.set(token);
    }

    logout(): void{
        console.log('deslogeando')
        this._accessToken.set(null);
        this._user.set(null);
        /* Limpiaria las cookies el refreshToken */
    }
}