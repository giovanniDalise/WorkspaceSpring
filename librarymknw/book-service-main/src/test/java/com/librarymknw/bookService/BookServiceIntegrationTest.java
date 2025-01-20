package com.librarymknw.bookService;

import com.librarymknw.bookService.application.BookServiceMainApplication;
import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import com.librarymknw.bookService.infrastructure.adapters.BookREST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookServiceMainApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class BookServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookRepositoryPort bookRepository;

    @Autowired
    private BookREST bookService;

    private Book book;

    @BeforeEach
    public void setUp() {
        // Configura un libro di test
        book = new Book();
        book.setBookId(1L);
        book.setTitle("Test Book");
        book.setIsbn("123-456-789");
    }


    @Test
    public void contextLoads() {
        // Questo test verifica semplicemente che il contesto venga caricato
    }

    @Test
    public void testGetBooks() throws Exception {
        // Mocka il comportamento del repository
        List<Book> books = Arrays.asList(book);
        when(bookRepository.read()).thenReturn(books);

        // Testa la richiesta GET per ottenere tutti i libri
        mockMvc.perform(get("/library")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookRepository.getById(1L)).thenReturn(book);

        mockMvc.perform(get("/library/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookRepository.create(any(Book.class))).thenReturn(1L);

        mockMvc.perform(post("/library")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Book\",\"isbn\":\"123-456-789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    public void testUpdateBook() throws Exception {
        when(bookRepository.update(anyLong(), any(Book.class))).thenReturn(1L);

        mockMvc.perform(put("/library/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Test Book\",\"isbn\":\"987-654-321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    public void testDeleteBook() throws Exception {
        when(bookRepository.delete(1L)).thenReturn(1L);

        mockMvc.perform(delete("/library/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    public void testFindBooksByString() throws Exception {
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findByText("Test")).thenReturn(books);

        mockMvc.perform(get("/library/findByString?param=Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    public void testFindBooksByBook() throws Exception {
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findByObject(any(Book.class))).thenReturn(books);

        mockMvc.perform(post("/library/findByBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }
}
