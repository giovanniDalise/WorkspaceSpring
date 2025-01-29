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

      if (token) {
        // Decode the token
        const decodedToken: any = jwtDecode(token);

        // Check if the role is "admin" and update the variable
        this.isAdmin = decodedToken.role === 'admin';
        this.isAuthenticated = true;  // Set as authenticated if token is present
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
}
