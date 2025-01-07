package com.librarymknw.bookService.infrastructure.adapters;

import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@CrossOrigin(origins = "*")
public class BookREST {

    private final BookRepositoryPort bookRepository;

    public BookREST(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        try {
            List<Book> books = bookRepository.read();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Long> addBook(@RequestBody Book book) {
        try {
            Long createdBookId = bookRepository.create(book);
            return ResponseEntity.ok(createdBookId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {
        try {
            Long deletedBookId = bookRepository.delete(id);
            return ResponseEntity.ok(deletedBookId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            Long updatedBookId = bookRepository.update(id, book);
            return ResponseEntity.ok(updatedBookId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Book book = bookRepository.getById(id);
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
            List<Book> books = bookRepository.findByText(param);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/findByBook")
    public ResponseEntity<List<Book>> findBooksByBook(@RequestBody Book book) {
        try {
            List<Book> books = bookRepository.findByObject(book);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
