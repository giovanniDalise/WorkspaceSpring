package com.librarymknw.reservationService.core.domain.events;

import com.librarymknw.reservationService.core.domain.models.Reservation;

public class ReservationEvent {
    private String type;
    private Reservation reservation;

    public ReservationEvent(String type, Reservation reservation) {
        this.type = type;
        this.reservation = reservation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
