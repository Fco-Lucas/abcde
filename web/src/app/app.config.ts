import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withHashLocation } from '@angular/router';

import { routes } from './app.routes';
import { provideNgxMask } from 'ngx-mask';
import { provideHttpClient, withInterceptors, withFetch } from '@angular/common/http';
import { errorHandlingInterceptor } from './core/interceptors/error-handling.interceptor';
import { authTokenInterceptor } from './core/interceptors/auth-token.interceptor';
import { CookieService } from 'ngx-cookie-service';
import { CustomPaginatorIntl } from './core/services/custom-paginator-intl.service';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers: [
    provideAnimationsAsync(),
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withHashLocation()),
    provideNgxMask(),
    provideHttpClient(
      withInterceptors([errorHandlingInterceptor, authTokenInterceptor]),
      withFetch()
    ),
    CookieService,
    { provide: MatPaginatorIntl, useClass: CustomPaginatorIntl },
  ]
};
