import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import {
  computed,
  inject,
  Injectable,
  PLATFORM_ID,
  Signal,
  signal,
  TransferState,
} from '@angular/core';
import { API_URL } from '@configs/tokens';
import { catchError, finalize, Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _user = signal<User | null>(null);
  private _accessToken = signal<string | null>(null);
 
  private apiUrl = inject(API_URL);
  private http = inject(HttpClient);

  public user: Signal<User | null> = this._user.asReadonly();
  public accessToken: Signal<string | null> = this._accessToken.asReadonly();
  public _loading = signal<boolean>(false);
  public isAuthenticated = computed<boolean>(() => !!this._user() && !!this.accessToken());

  //private transferState = inject(TransferState);
  private platformId = inject(PLATFORM_ID);

  init() {
    if (isPlatformBrowser(this.platformId)) {
      this.fetchUser().subscribe({
        next: (user) => {
          console.log('login with user: ', user);
          this._user.set(user);
        },
      });
    }
  }

  login({ email, password }: LoginRequest): Observable<AccessToken> {
    return this.http
      .post<AccessToken>(
        this.apiUrl + '/auth/login',
        { email, password },
        { withCredentials: true }
      )
      .pipe(tap((response) => {
        this._accessToken.set(response.access_token)
      })
    );
  }

  register({
    email,
    password,
    profile_url,
    username
  }: RegisterRequest): Observable<AccessToken> {

    return this.http
      .post<AccessToken>(
        this.apiUrl + '/auth/register',
        {
          email,
          password,
          profile_url,
          username,
        },
        { withCredentials: true }
      )
      .pipe(tap((response) => this._accessToken.set(response.access_token)));
  }

  fetchUser(): Observable<User> {
    console.log('fetch user with token ' + this.accessToken());

    return this.http
      .get<User>(this.apiUrl + '/auth/me', {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${this.accessToken()}`,
        },
      })
      .pipe(tap((user) => this._user.set(user)));
  }

  setToken(token: string) {
    if (!token) return;
    this._accessToken.set(token);
  }

  logout(): void {
    console.log('deslogeando');
    this._accessToken.set(null);
    this._user.set(null);
    /* Limpiaria las cookies el refreshToken */
  }
}
