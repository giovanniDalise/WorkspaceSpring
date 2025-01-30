package com.librarymknw.authService.core.exceptions;

public class AuthRepositoryException extends RuntimeException {
    public AuthRepositoryException(String message) {
        super(message);
    }

    public AuthRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }
}
