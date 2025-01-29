import { HttpEvent, HttpRequest, HttpInterceptorFn, HttpHandlerFn } from '@angular/common/http';
import { Observable } from 'rxjs';
import { inject } from '@angular/core'; // Iniettare direttamente il servizio
import { AuthenticationService } from '../services/authentication.service';

export const JwtInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  const authService = inject(AuthenticationService);  // Iniezione del servizio direttamente nell'interceptor
  const token = authService.getToken();  // Usa il servizio per ottenere il token
  if (token) {
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedReq);
  }
  return next(req);
};
