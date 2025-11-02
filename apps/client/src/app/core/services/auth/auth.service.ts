import { HttpClient } from "@angular/common/http";
import { computed, inject, Injectable, Signal, signal } from "@angular/core";
import { API_URL } from "@configs/tokens";
import { Observable, tap } from "rxjs";





@Injectable({providedIn:'root'})
export class AuthService{
    
    private _user = signal<User | null>(null);    
    private _accessToken = signal<string|null>(null);

    private apiUrl = inject(API_URL);
    private http = inject(HttpClient)
    

    public user: Signal<User | null> = this._user.asReadonly();
    public accessToken: Signal<string | null> = this._accessToken.asReadonly();
    public isAuthenticated = computed(() => !!this._user());

    
    login({ email, password }: LoginRequest): Observable<AccessToken>{
        return this.http.post<AccessToken>(this.apiUrl + "/auth/login", { email, password }, { withCredentials: true }).pipe(
            tap(response => this._accessToken.set(response.access_token))
        );
    }


    register({ email, password, profile_url, username, verify_password }: RegiterRequest): Observable<AccessToken>{
        
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


    fetchUser(): Observable<User>{
        return this.http.get<User>(this.apiUrl + '/auth/me', {
            withCredentials: true,
            headers: {
                'Authorization': `Bearer ${this._accessToken()}`
            }
         }).pipe(tap(response=> this._user.set(response)));
    }

    logout(): void{
        this._accessToken.set(null);
        this._user.set(null);
        /* Limpiaria las cookies el refreshToken */
    }
}