package com.librarymknw.bookService.core.ports;

import com.librarymknw.bookService.core.domain.models.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepositoryPort {
    List<Book> read();
    Book getById(Long index);
    List<Book> findByText(String searchText);
    List<Book> findByObject(Book searchBook);
    Long create(Book book) ;
    Long update(Long bookId, Book Book);
    Long delete(Long bookId);
}