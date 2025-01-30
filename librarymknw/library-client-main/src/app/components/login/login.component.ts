import { Component } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule], 
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  constructor(private auth: AuthenticationService, private router: Router) {}

  login() {
    if (this.email && this.password) {
      this.auth.login(this.email, this.password).subscribe({
        next: (resp) => {
          // Usa localStorage invece di sessionStorage per la persistenza del token
          if (typeof window !== 'undefined' && window.sessionStorage) {
            sessionStorage.setItem("authToken", resp.token); // 
          }
          this.router.navigate(['home']);
        },
        error: () => {
          alert("Email o password non validi");
        }
      });
    }
  }
}
