package com.librarymknw.reservationService.core.domain.services;

import com.librarymknw.reservationService.core.domain.events.ReservationEvent;
import com.librarymknw.reservationService.core.domain.models.Reservation;
import com.librarymknw.reservationService.core.ports.ReservationRepositoryPort;
import com.librarymknw.reservationService.core.ports.ReservationServicePort;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService implements ReservationServicePort {

    private final ReservationRepositoryPort repository;
  //  private final KafkaTemplate<String, Object> kafkaTemplate;

/*    public ReservationService(ReservationRepositoryPort repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }*/

    public ReservationService(ReservationRepositoryPort repository) {
        this.repository = repository;
    }

    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return repository.findById(id);
    }

    public void createReservation(Reservation reservation) {
        repository.save(reservation);
   //     kafkaTemplate.send("reservation-events", new ReservationEvent("CREATED", reservation));
    }

    public void updateReservation(Long id, Reservation reservation) {
        repository.update(reservation);
    }

    public void deleteReservation(Long id) {
        repository.delete(id);
    }
}