package com.librarymknw.bookService.infrastructure.adapters;

import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import com.librarymknw.bookService.core.ports.BookServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@CrossOrigin(origins = "*")
public class BookREST {

    private final BookServicePort bookService;  // Usare BookServicePort invece di BookRepositoryPort

    public BookREST(BookServicePort bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> addBook(@RequestBody Book book) {
        try {
            Long createdBookId = bookService.createBook(book);
            return ResponseEntity.ok(createdBookId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {
        try {
            Long deletedBookId = bookService.deleteBook(id);
            return ResponseEntity.ok(deletedBookId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            Long updatedBookId = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBookId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getBookById(id);
            if (book == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/findByString")
    public ResponseEntity<List<Book>> findBooksByString(@RequestParam String param) {
        try {
            List<Book> books = bookService.getBooksByText(param);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/findByBook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Book>> findBooksByBook(@RequestBody Book book) {
        try {
            List<Book> books = bookService.getBooksByObject(book);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
