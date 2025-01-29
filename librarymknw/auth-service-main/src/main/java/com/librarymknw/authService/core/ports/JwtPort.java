package com.librarymknw.authService.core.ports;

public interface JwtPort {
    String generateJwtToken(String email); // Solo l'email per generare il token
}
