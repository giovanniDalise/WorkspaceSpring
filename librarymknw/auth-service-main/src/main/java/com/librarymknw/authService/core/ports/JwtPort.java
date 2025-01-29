package com.librarymknw.authService.core.ports;

public interface JwtPort {
    String generateJwtToken(String email, String role); // Solo l'email per generare il token
}
