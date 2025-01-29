package com.librarymknw.reservationService.infrastructure.adapters;

import com.librarymknw.reservationService.core.domain.models.Reservation;
import com.librarymknw.reservationService.core.ports.ReservationRepositoryPort;
import com.librarymknw.reservationService.core.ports.ReservationServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/reservations")
public class ReservationREST {

    private final ReservationServicePort reservationService;  // Usare BookServicePort invece di BookRepositoryPort

    @Autowired
    public ReservationREST(ReservationServicePort reservationService) {
        this.reservationService = reservationService;
    }

    // Endpoint per ottenere tutte le prenotazioni
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Endpoint per ottenere una prenotazione per id
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    // Endpoint per creare una nuova prenotazione
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            reservationService.createReservation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (RuntimeException e) {
            // Log dell'errore
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la creazione della prenotazione: " + e.getMessage());
        }
    }

    // Endpoint per aggiornare una prenotazione esistente
    @PutMapping("/{id}")
    public void updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservation.setId(id); // Assicurati che l'id venga settato
        reservationService.updateReservation(id, reservation);
    }

    // Endpoint per eliminare una prenotazione
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
