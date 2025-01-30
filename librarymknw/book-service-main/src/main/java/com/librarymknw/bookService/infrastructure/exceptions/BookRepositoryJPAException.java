package com.librarymknw.bookService.infrastructure.exceptions;

import com.librarymknw.bookService.core.exceptions.BookRepositoryException;

public class BookRepositoryJPAException extends BookRepositoryException {
    public BookRepositoryJPAException(String message) {
        super(message);
    }

    public BookRepositoryJPAException(String message, Throwable cause) {
        super(message, cause);
    }
}