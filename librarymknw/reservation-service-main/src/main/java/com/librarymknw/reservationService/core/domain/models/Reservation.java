package com.librarymknw.reservationService.core.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Long id;
    private Long userId;
    private Long bookId;

    private LocalDate reservationDate;

    private LocalDate dueDate;

    public Reservation(){};

    public Reservation(Long userId, Long bookId, LocalDate reservationDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(userId, that.userId) && Objects.equals(bookId, that.bookId) && Objects.equals(reservationDate, that.reservationDate) && Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, reservationDate, dueDate);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", reservationDate=" + reservationDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
