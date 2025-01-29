import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface AuthResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private apiUrl = 'http://localhost:8081/auth/login'; // Modifica con l'URL corretto

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.apiUrl, { email, password });
  }

  logout(): void {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      sessionStorage.removeItem('token');
    }
  }

  isAuthenticated(): boolean {
    // Verifica che sessionStorage sia disponibile
    if (typeof window !== 'undefined' && window.sessionStorage) {
      return !!sessionStorage.getItem('token');
    }
    return false;
  }

  getToken(): string | null {
    // Controlla se sessionStorage è disponibile nel client
    if (typeof window !== 'undefined' && window.sessionStorage) {
      const token = sessionStorage.getItem("token");
      return token;
    }
    console.log("sessionStorage non disponibile"); // Debug
    return null;
  }
  
}
