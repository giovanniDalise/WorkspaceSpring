import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Book } from '../models/book.model';

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  private apiUrl = 'http://localhost:8080/library'

  constructor(private http:HttpClient) { }

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiUrl);
  }

  getBookById(bookId: number): Observable<Book> {
    return this.http.get<Book>(this.apiUrl + '/' + bookId);
  }

  getBookByText(text: string): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiUrl + '/' + 'findByString', { params: { param: text } })
  }

  deleteBook(bookId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${bookId}`);
  }

  createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.apiUrl, book)
  }

updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>(`${this.apiUrl}/${book.bookId}`, book)
  }

  findByObject(criteria: Partial<Book>, options?: { headers?: HttpHeaders }): Observable<Book[]> {
    return this.http.post<Book[]>(`${this.apiUrl}/findByBook`, criteria, options)
  }
  
}
