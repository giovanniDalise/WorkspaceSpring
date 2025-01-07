package com.librarymknw.bookService.infrastructure.exceptions;

import com.librarymknw.bookService.core.exceptions.RepositoryException;

public class RepositoryJPAException extends RepositoryException {
    public RepositoryJPAException(String message) {
        super(message);
    }

    public RepositoryJPAException(String message, Throwable cause) {
        super(message, cause);
    }
}