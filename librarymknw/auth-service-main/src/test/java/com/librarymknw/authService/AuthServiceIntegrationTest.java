
package com.librarymknw.authService;

import com.librarymknw.authService.application.AuthServiceMainApplication;
import com.librarymknw.authService.core.domain.models.AuthResponse;
import com.librarymknw.authService.core.domain.models.LoginRequest;
import com.librarymknw.authService.core.ports.AuthenticationServicePort;
import com.librarymknw.authService.core.ports.JwtPort;
import com.librarymknw.authService.core.ports.AuthenticationRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("local")
@SpringBootTest (classes = AuthServiceMainApplication.class)
@AutoConfigureMockMvc
public class AuthServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtPort jwtPort; // Mock per JwtPort

    @MockitoBean
    private AuthenticationRepositoryPort authenticationRepositoryPort; // Mock per AuthenticationRepositoryPort

    @MockitoBean
    private AuthenticationServicePort authenticationService; // Il servizio che stiamo testando

    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        // Prepara i dati di login di test
        loginRequest = new LoginRequest("user@example.com", "password123");
    }

    @Test
    public void contextLoads() {
        // Questo test verifica semplicemente che il contesto venga caricato
    }


    @Test
    public void testAuthenticate_validCredentials() throws Exception {
        // Dati di input per la richiesta
        String email = "user@example.com";
        String password = "password123";
        String tokenMock = "jwt_token_mock";

        // Creazione di un oggetto LoginRequest
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Mock del comportamento di check delle credenziali
        when(authenticationRepositoryPort.checkUserCredentials(email, password))
                .thenReturn(true);

        // Mock della generazione del token JWT
        when(jwtPort.generateJwtToken(email, "ADMIN"))
                .thenReturn(tokenMock);

        // Mock del servizio di autenticazione per restituire un AuthResponse
        // Si usa il metodo any(LoginRequest.class) per fare match su qualsiasi oggetto LoginRequest
        when(authenticationService.authenticate(any(LoginRequest.class)))
                .thenReturn(new AuthResponse(tokenMock));

        // Test della richiesta POST per l'autenticazione
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())  // Verifica che la risposta sia 200 OK
                .andExpect(jsonPath("$.token").value(tokenMock))  // Verifica il token restituito
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())); // Debug
    }


    @Test
    public void testAuthenticate_invalidCredentials() throws Exception {
        // Mock dei comportamenti per credenziali non valide
        when(authenticationRepositoryPort.checkUserCredentials("user@example.com", "wrongpassword")).thenReturn(false);

        // Test della richiesta POST con credenziali errate
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized());  // Controlla che la risposta sia 401 Unauthorized
    }

    @Test
    public void testAuthenticate_emptyCredentials() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"\", \"password\":\"\"}"))
                .andExpect(status().isUnauthorized())  // Modifica qui per 401
                .andExpect(content().string("Email o password non validi"));  // Verifica la stringa nel corpo della risposta
    }

}

