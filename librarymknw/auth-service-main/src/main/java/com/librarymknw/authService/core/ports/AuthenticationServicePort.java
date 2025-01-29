package com.librarymknw.authService.core.ports;

import com.librarymknw.authService.core.domain.models.AuthResponse;
import com.librarymknw.authService.core.domain.models.LoginRequest;

public interface AuthenticationServicePort {
    AuthResponse authenticate(LoginRequest loginRequest);  // Usa lo stesso nome del metodo della classe concreta
}
