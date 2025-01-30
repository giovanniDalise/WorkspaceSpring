package com.librarymknw.authService.infrastructure.exceptions;

import com.librarymknw.authService.core.exceptions.AuthRepositoryException;

public class AuthRepositoryJDBCException extends AuthRepositoryException {

    public AuthRepositoryJDBCException(String message) { super(message); }

    public AuthRepositoryJDBCException(String message, Throwable cause) { super(message, cause); }

}
