import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RedirectGuard implements CanActivate {
  constructor(private authService: AuthenticationService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      // Se l'utente è autenticato, reindirizza alla home
      this.router.navigate(['/home']);
      return false;  // Non lascia passare all'accesso
    }
    return true;  // Permette l'accesso alla pagina di login
  }
}
