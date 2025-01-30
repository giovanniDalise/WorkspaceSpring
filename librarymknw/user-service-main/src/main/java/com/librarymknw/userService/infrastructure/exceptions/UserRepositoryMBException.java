package com.librarymknw.userService.infrastructure.exceptions;

import com.librarymknw.userService.core.exceptions.UserRepositoryException;

public class UserRepositoryMBException extends UserRepositoryException {
    public UserRepositoryMBException(String message) {
        super(message);
    }

    public UserRepositoryMBException(String message, Throwable cause) {
        super(message, cause);
    }
}
