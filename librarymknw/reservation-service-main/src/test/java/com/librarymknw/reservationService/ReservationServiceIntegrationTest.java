package com.librarymknw.reservationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.librarymknw.reservationService.application.ReservationServiceMainApplication;
import com.librarymknw.reservationService.core.domain.models.Reservation;
import com.librarymknw.reservationService.core.ports.ReservationRepositoryPort;
import com.librarymknw.reservationService.infrastructure.adapters.ReservationREST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = ReservationServiceMainApplication.class)
@AutoConfigureMockMvc
public class ReservationServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationRepositoryPort reservationRepository;

    @Autowired
    private ReservationREST reservationRest;

    private Reservation reservation;


    @BeforeEach
    public void setUp() {
        // Inizializza una prenotazione di test
        reservation = new Reservation(1L, 1L, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 30));

        // Crea ObjectMapper con configurazione per le date
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Gestisce LocalDate
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);  // Usa il formato ISO per le date

        // Configura MockMvc per utilizzare il controller e il mock repository
        mockMvc = MockMvcBuilders.standaloneSetup(reservationRest)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void contextLoads() {
        // Questo test verifica semplicemente che il contesto venga caricato
    }

    @Test
    public void testGetAllReservations() throws Exception {
        // Prepara il comportamento del mock
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Esegui la richiesta GET e verifica la risposta
        mockMvc.perform(get("/library/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].bookId").value(1L))
                .andExpect(jsonPath("$[0].reservationDate").value("2024-01-15"))
                .andExpect(jsonPath("$[0].dueDate").value("2024-01-30"));
    }

    @Test
    public void testGetReservationById() throws Exception {
        // Prepara il comportamento del mock
        when(reservationRepository.findById(1L)).thenReturn(reservation);

        // Esegui la richiesta GET per recuperare la prenotazione per id
        mockMvc.perform(get("/library/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.bookId").value(1L))
                .andExpect(jsonPath("$.reservationDate").value("2024-01-15"))
                .andExpect(jsonPath("$.dueDate").value("2024-01-30"));
    }
    @Test
    public void testCreateReservation() throws Exception {
        // Prepara il comportamento del mock
        doNothing().when(reservationRepository).save(any(Reservation.class));

        // Esegui la richiesta POST per creare una nuova prenotazione
        mockMvc.perform(post("/library/reservations")
                        .contentType("application/json")
                        .content("{\"userId\":1,\"bookId\":1,\"reservationDate\":\"2024-01-15\",\"dueDate\":\"2024-01-30\"}"))
                .andExpect(status().isCreated()); // Expect 201 Created
    }

    @Test
    public void testUpdateReservation() throws Exception {
        // Prepara il comportamento del mock
        doNothing().when(reservationRepository).update(any(Reservation.class));

        // Esegui la richiesta PUT per aggiornare una prenotazione
        mockMvc.perform(put("/library/reservations/1")
                        .contentType("application/json")
                        .content("{\"userId\":1,\"bookId\":1,\"reservationDate\":\"2024-02-01\",\"dueDate\":\"2024-02-15\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteReservation() throws Exception {
        // Prepara il comportamento del mock
        doNothing().when(reservationRepository).delete(1L);

        // Esegui la richiesta DELETE per eliminare una prenotazione
        mockMvc.perform(delete("/library/reservations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateReservationWithException() throws Exception {
        // Simula l'errore nel repository
        doThrow(new RuntimeException("Error")).when(reservationRepository).save(any(Reservation.class));

        // Esegui la richiesta POST per creare una prenotazione e verifica l'errore
        mockMvc.perform(post("/library/reservations")
                        .contentType("application/json")
                        .content("{\"userId\":1,\"bookId\":1,\"reservationDate\":\"2024-01-15\",\"dueDate\":\"2024-01-30\"}"))
                .andExpect(status().isInternalServerError());
    }

}
