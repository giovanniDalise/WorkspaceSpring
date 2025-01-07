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
    return this.http.get<Book[]>(this.apiUrl).pipe(
      map((books: any[]) => 
        books.map(book => ({
          bookId: book.bookId, // ho rifattorizzato i progetti perchè ti arrivava id dal json e il tuo modello utilizza invece bookId
          //quello che ha in questa parte del map ti ricordo che a sinistra è il tuo modello invece a destra è come ti arriva dall'api se nei metidi
          //sotto del servizio vedi che per l'api stai utilizzando stranamente bookId è probabile sia dovuto a qualche chiamata del metodo che 
          //cambia il nome
          title: book.title,
          isbn: book.isbn,
          authors: book.authors, // Mantieni solo il campo authors
          editor: book.editor // Assumi che 'editor' sia presente nel JSON
        }))
      )
    ); 
  }

  getBookById(bookId: number): Observable<Book> {
    return this.http.get<Book>(this.apiUrl + '/' + bookId).pipe(  // Passa il bookId nell'URL
      map(book => ({
        bookId: book.bookId, // Assumi che 'id' sia il campo nel JSON restituito
        title: book.title,
        isbn: book.isbn,
        authors: book.authors, // Mantieni solo il campo authors
        editor: book.editor   // Assumi che 'editor' sia presente nel JSON
      }))
    );
  }

  getBookByText(text: string): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiUrl + '/' + 'findByString', { params: { param: text } }).pipe(
      map((books: any[]) => 
        books.map(book => ({
          bookId: book.bookId, 
          title: book.title,
          isbn: book.isbn,
          authors: book.authors, 
          editor: book.editor 
        }))
      )
    );
  }

  createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.apiUrl, book).pipe( 
      map(createdBook => ({
        bookId: createdBook.bookId,
        title: createdBook.title,
        isbn: createdBook.isbn,
        authors: createdBook.authors,
        editor: createdBook.editor
      }))
    );
  }

updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>(`${this.apiUrl}/${book.bookId}`, book).pipe( 
      map(updatedBook => ({
        bookId: updatedBook.bookId,
        title: updatedBook.title,
        isbn: updatedBook.isbn,
        authors: updatedBook.authors,
        editor: updatedBook.editor
      }))
    );
  }

  findByObject(criteria: Partial<Book>, options?: { headers?: HttpHeaders }): Observable<Book[]> {
    return this.http.post<Book[]>(`${this.apiUrl}/findByBook`, criteria, options).pipe(
      map((books: any[]) =>
        books.map(book => ({
          bookId: book.bookId,
          title: book.title,
          isbn: book.isbn,
          authors: book.authors,
          editor: book.editor
        }))
      )
    );
  }

  deleteBook(bookId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${bookId}`);
  }
  
}
