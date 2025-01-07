package com.librarymknw.reservationService.core.ports;

import com.librarymknw.reservationService.core.domain.models.Reservation;

import java.util.List;

public interface ReservationServicePort {
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    void createReservation(Reservation reservation);
    void updateReservation(Long id, Reservation reservation);
    void deleteReservation(Long id);
}
