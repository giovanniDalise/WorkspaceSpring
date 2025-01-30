package com.librarymknw.reservationService.infrastructure.exceptions;

import com.librarymknw.reservationService.core.exceptions.ReservationRepositoryException;

public class ReservationRepositoryMBException extends ReservationRepositoryException {
    public ReservationRepositoryMBException(String message) {
        super(message);
    }

    public ReservationRepositoryMBException(String message, Throwable cause) {
        super(message, cause);
    }
}
