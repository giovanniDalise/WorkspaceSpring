package com.librarymknw.bookService.core.ports;

import com.librarymknw.bookService.core.domain.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookServicePort {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    List<Book> getBooksByText(String searchText);
    List<Book> getBooksByObject(Book searchBook);
    Long createBook(Book book);
    Long updateBook(Long id, Book book);
    Long deleteBook(Long id);
}
