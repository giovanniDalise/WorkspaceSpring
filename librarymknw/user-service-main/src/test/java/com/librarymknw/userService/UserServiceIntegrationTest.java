package com.librarymknw.userService;

import com.librarymknw.userService.application.UserServiceMainApplication;
import com.librarymknw.userService.core.domain.models.User;
import com.librarymknw.userService.core.ports.UserRepositoryPort;
import com.librarymknw.userService.infrastructure.adapters.UserREST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UserServiceMainApplication.class)
@AutoConfigureMockMvc
public class UserServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepositoryPort userRepository;

    @Autowired
    private UserREST userRest;

    private User user;

    @BeforeEach
    public void setUp() {
        // Inizializza un utente di test
        user = new User("John Doe", "john.doe@example.com");

        // Inizializza MockMvc con il controller da testare
        mockMvc = MockMvcBuilders.standaloneSetup(userRest).build();
    }

    @Test
    public void contextLoads() {
        // Questo test verifica semplicemente che il contesto venga caricato
    }

   @Test
    public void testGetAllUsers() throws Exception {
        // Prepara il comportamento del mock
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        // Esegui la richiesta GET e verifica la risposta
        mockMvc.perform(get("/library/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

     @Test
    public void testGetUserById() throws Exception {
        // Prepara il comportamento del mock
        when(userRepository.findById(1L)).thenReturn(user);

        // Esegui la richiesta GET per recuperare l'utente per id
        mockMvc.perform(get("/library/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        // Prepara il comportamento del mock
        doNothing().when(userRepository).save(any(User.class));  // Modificato con doNothing()

        // Esegui la richiesta POST per creare un nuovo utente
        mockMvc.perform(post("/library/users")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isCreated()); // Expect 201 Created
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Prepara il comportamento del mock
        doNothing().when(userRepository).update(any(User.class));  // Modificato con doNothing()

        // Esegui la richiesta PUT per aggiornare un utente
        mockMvc.perform(put("/library/users/1")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe Updated\",\"email\":\"john.updated@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Prepara il comportamento del mock
        doNothing().when(userRepository).delete(1L);

        // Esegui la richiesta DELETE per eliminare un utente
        mockMvc.perform(delete("/library/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateUserWithException() throws Exception {
        // Simula l'errore nel repository
        doThrow(new RuntimeException("Error")).when(userRepository).save(any(User.class));

        // Esegui la richiesta POST per creare un utente e verifica l'errore
        mockMvc.perform(post("/library/users")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isInternalServerError());
    }

}
