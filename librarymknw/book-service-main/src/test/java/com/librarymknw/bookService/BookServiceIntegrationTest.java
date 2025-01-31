package com.librarymknw.bookService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.librarymknw.bookService.application.BookServiceMainApplication;
import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import com.librarymknw.bookService.infrastructure.adapters.BookREST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@SpringBootTest(classes = BookServiceMainApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-local.properties")
public class BookServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookRepositoryPort bookRepository;

    @Autowired
    private BookREST bookService;

    private Book book;
    private String token;

    private final String SECRET_KEY = "mySecretKey"; // Chiave segreta, usa la stessa che nel filtro

    @BeforeEach
    public void setUp() {
        // Configura un libro di test
        book = new Book();
        book.setBookId(1L);
        book.setTitle("Test Book");
        book.setIsbn("123-456-789");

        // Mocka il comportamento del repository
        List<Book> books = List.of(book);
        when(bookRepository.read()).thenReturn(books);

        // Genera il token JWT
        token = generateToken();
    }

    // Metodo per generare un token JWT
    private String generateToken() {
        return JWT.create()
                .withSubject("testUser")  // Puoi usare un nome utente di test
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 ora di validità
                .sign(Algorithm.HMAC512(SECRET_KEY));  // Usa la stessa chiave segreta
    }

    @Test
    public void contextLoads() {
        // Questo test verifica semplicemente che il contesto venga caricato
    }



    @Test
    public void testGetBooks() throws Exception {
        // Testa la richiesta GET per ottenere tutti i libri con il token
        mockMvc.perform(MockMvcRequestBuilders.get("/library")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Book"));
    }


    @Test
    public void testGetBookById() throws Exception {
        // Mocka il comportamento del repository
        when(bookRepository.getById(1L)).thenReturn(book);

        // Ottieni il token JWT da generare
        String token = generateToken();

        // Testa la richiesta GET per ottenere il libro con id 1
        mockMvc.perform(get("/library/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)) // Aggiungi il token nell'header
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }


    @Test
    public void testCreateBook() throws Exception {
        // Mocka il comportamento del repository per creare il libro
        when(bookRepository.create(any(Book.class))).thenReturn(1L);

        // Ottieni il token JWT da generare
        String token = generateToken();

        // Testa la richiesta POST per creare un nuovo libro
        mockMvc.perform(post("/library")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Aggiungi il token nell'header
                        .content("{\"title\":\"Test Book\",\"isbn\":\"123-456-789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1)); // Verifica che il risultato sia 1 (ID del libro creato)
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Mocka il comportamento del repository per l'aggiornamento del libro
        when(bookRepository.update(anyLong(), any(Book.class))).thenReturn(1L);

        // Ottieni il token JWT da generare
        String token = generateToken();

        // Testa la richiesta PUT per aggiornare un libro esistente
        mockMvc.perform(put("/library/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Aggiungi il token nell'header
                        .content("{\"title\":\"Updated Test Book\",\"isbn\":\"987-654-321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1)); // Verifica che il risultato sia 1 (ID del libro aggiornato)
    }


    @Test
    public void testDeleteBook() throws Exception {
        // Mocka il comportamento del repository per la cancellazione del libro
        when(bookRepository.delete(1L)).thenReturn(1L);

        // Ottieni il token JWT da generare
        String token = generateToken();

        // Testa la richiesta DELETE per cancellare il libro con ID 1
        mockMvc.perform(delete("/library/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)) // Aggiungi il token nell'header
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1)); // Verifica che il risultato sia 1 (ID del libro cancellato)
    }

    @Test
    public void testFindBooksByString() throws Exception {
        // Mocka il comportamento del repository per trovare i libri in base alla stringa
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findByText("Test")).thenReturn(books);

        // Ottieni il token JWT da generare
        String token = generateToken();

        // Testa la richiesta GET per cercare libri con una stringa di parametro
        mockMvc.perform(get("/library/findByString?param=Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)) // Aggiungi il token nell'header
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }


    @Test
    public void testFindBooksByBook() throws Exception {
        // Mocka il comportamento del repository per trovare i libri
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findByObject(any(Book.class))).thenReturn(books);

        // Ottieni il token JWT da generare
        String token = generateToken();

        // Testa la richiesta POST per trovare i libri con titolo "Test Book"
        mockMvc.perform(post("/library/findByBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Aggiungi il token nell'header
                        .content("{\"title\":\"Test Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }
}

