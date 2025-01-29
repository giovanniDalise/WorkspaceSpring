package com.librarymknw.authService.core.ports;

import com.librarymknw.authService.core.domain.models.AuthResponse;
import com.librarymknw.authService.core.domain.models.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationRepositoryPort {
    boolean checkUserCredentials(String email, String password); // Verifica delle credenziali
    public String getUserRole(String email);
}
