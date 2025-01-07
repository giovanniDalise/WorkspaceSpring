package com.librarymknw.bookService.core.domain.services;

import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import com.librarymknw.bookService.core.ports.BookServicePort;
import com.librarymknw.bookService.core.domain.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements BookServicePort {

    private final BookRepositoryPort bookRepositoryPort;

    public BookService(BookRepositoryPort bookRepositoryPort) {
        this.bookRepositoryPort = bookRepositoryPort;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepositoryPort.read();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepositoryPort.getById(id);
    }

    @Override
    public Long createBook(Book book) {
        return bookRepositoryPort.create(book);
    }

    @Override
    public Long updateBook(Long id, Book book) {
        return bookRepositoryPort.update(id, book);
    }

    @Override
    public Long deleteBook(Long id) {
        return bookRepositoryPort.delete(id);
    }

    @Override
    public List<Book> getBooksByText(String searchText) {
        return bookRepositoryPort.findByText(searchText);
    }

    @Override
    public List<Book> getBooksByObject(Book searchBook) {
        return bookRepositoryPort.findByObject(searchBook);
    }
}

