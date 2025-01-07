package com.librarymknw.reservationService.core.ports;

import com.librarymknw.reservationService.core.domain.models.Reservation;

import java.util.List;

public interface ReservationRepositoryPort {
    List<Reservation> findAll();
    Reservation findById(Long id);
    void save(Reservation reservation);
    void update(Reservation reservation);
    void delete(Long id);
}
