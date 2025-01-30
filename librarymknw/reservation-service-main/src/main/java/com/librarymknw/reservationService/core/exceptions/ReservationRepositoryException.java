package com.librarymknw.reservationService.core.exceptions;

public class ReservationRepositoryException extends RuntimeException {
    public ReservationRepositoryException(String message) {
        super(message);
    }

    public ReservationRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}