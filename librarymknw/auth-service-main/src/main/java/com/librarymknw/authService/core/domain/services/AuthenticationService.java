package com.librarymknw.authService.core.domain.services;

import com.librarymknw.authService.core.domain.models.AuthResponse;
import com.librarymknw.authService.core.domain.models.LoginRequest;
import com.librarymknw.authService.core.ports.AuthenticationServicePort;
import com.librarymknw.authService.core.ports.JwtPort;
import com.librarymknw.authService.core.ports.AuthenticationRepositoryPort;
import org.springframework.stereotype.Service;

public class AuthenticationService  implements AuthenticationServicePort {

    private final JwtPort jwtPort;
    private final AuthenticationRepositoryPort repositoryPort;

    public AuthenticationService(JwtPort jwtPort, AuthenticationRepositoryPort repositoryPort) {
        this.jwtPort = jwtPort;
        this.repositoryPort = repositoryPort;
    }

    public AuthResponse authenticate(LoginRequest loginRequest) {
        // Verifica se l'utente esiste nel database con le credenziali fornite
        boolean authenticated = repositoryPort.checkUserCredentials(loginRequest.getEmail(), loginRequest.getPassword());
        if (authenticated) {
            // Se l'autenticazione è riuscita, generiamo il token JWT
            String token = jwtPort.generateJwtToken(loginRequest.getEmail());
            return new AuthResponse(token); // Restituiamo il token
        }
        return null; // Se non autenticato, restituiamo null
    }
}
