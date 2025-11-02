import { ApplicationConfig } from "@angular/core";
import { API_URL } from "./tokens";
import { environment } from "./environment";
import { provideHttpClient, withFetch, withInterceptors } from "@angular/common/http";
import { authInterceptor } from "@core/interceptors/auth-api.interceptor";




export const globalConfig: ApplicationConfig = {
    providers: [
        provideHttpClient(withFetch(),withInterceptors([authInterceptor])),
        {provide: API_URL, useValue: environment.apiUrl}
    ]
}