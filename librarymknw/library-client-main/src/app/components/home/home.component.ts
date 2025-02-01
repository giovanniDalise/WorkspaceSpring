import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, FormsModule ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  bookId: number | undefined;
  text: string = '';
  isAdmin: boolean = false;
  isAuthenticated: boolean = false;
  imageFile = "img/libraryBackground.jpg";

  constructor(private router: Router, private authService: AuthenticationService) {}

  ngOnInit() {
    if (this.isBrowser()) {
      const token = sessionStorage.getItem('authToken');  // Retrieve the JWT token
    
      if (token) {
        // Decodifica del token
        const decodedToken: any = jwtDecode(token);
  
        // Verifica se il ruolo è "admin" e aggiorna la variabile
        this.isAdmin = decodedToken.role === 'admin';
  
        this.isAuthenticated = true;  // Imposta come autenticato se il token è presente
      } else {
        this.isAuthenticated = false;
      }
    }
  }
  

  // Metodo per verificare se siamo nel contesto del browser
  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  searchBookById(): void {
    if (this.bookId) {
      this.router.navigate(['/books-view', this.bookId]);
    }
  }

  searchBookByText(): void {
    if (this.text) {
        this.router.navigate(['/books-view'], { queryParams: { search: this.text } });
      }
  }

  logout(): void {
    this.authService.logout();
    this.isAuthenticated = false; // Aggiorna lo stato locale
    this.isAdmin = false;
    this.router.navigate(['/login']); // Reindirizza alla pagina di login
  }
}