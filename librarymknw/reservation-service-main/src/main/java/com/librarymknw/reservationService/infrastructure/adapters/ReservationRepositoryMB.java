package com.librarymknw.reservationService.infrastructure.adapters;

import com.librarymknw.reservationService.core.domain.models.Reservation;
import com.librarymknw.reservationService.core.ports.ReservationRepositoryPort;
import com.librarymknw.reservationService.infrastructure.mappers.ReservationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepositoryMB implements ReservationRepositoryPort {

    private final ReservationMapper reservationMapper;

    public ReservationRepositoryMB(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationMapper.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationMapper.findById(id);
    }

    @Override
    public void save(Reservation reservation) {
        reservationMapper.save(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        reservationMapper.update(reservation);
    }

    @Override
    public void delete(Long id) {
        reservationMapper.delete(id);
    }
}
