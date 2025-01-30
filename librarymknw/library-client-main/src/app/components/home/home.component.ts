import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

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

  constructor(private router: Router) {}

  ngOnInit() {
    if (this.isBrowser()) {
      const token = sessionStorage.getItem('authToken');  // Retrieve the JWT token
  
      console.log('Token recuperato:', token);  // Log per vedere se il token è presente nel sessionStorage
  
      if (token) {
        // Decodifica del token
        const decodedToken: any = jwtDecode(token);
        console.log('Token decodificato:', decodedToken);
  
        // Verifica se il ruolo è "admin" e aggiorna la variabile
        this.isAdmin = decodedToken.role === 'admin';
        console.log('Ruolo dell\'utente:', decodedToken.role); // Log per vedere il ruolo
  
        this.isAuthenticated = true;  // Imposta come autenticato se il token è presente
      } else {
        this.isAuthenticated = false;
        console.log('Token non trovato, utente non autenticato');
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
}