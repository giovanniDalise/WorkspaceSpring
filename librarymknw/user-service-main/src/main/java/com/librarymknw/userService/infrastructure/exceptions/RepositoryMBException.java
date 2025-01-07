package com.librarymknw.userService.infrastructure.exceptions;

import com.librarymknw.userService.core.exceptions.RepositoryException;

public class RepositoryMBException extends RepositoryException {
    public RepositoryMBException(String message) {
        super(message);
    }

    public RepositoryMBException(String message, Throwable cause) {
        super(message, cause);
    }
}
