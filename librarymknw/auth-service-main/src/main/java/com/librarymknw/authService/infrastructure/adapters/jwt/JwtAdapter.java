package com.librarymknw.authService.infrastructure.adapters.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.librarymknw.authService.core.ports.JwtPort;
import org.springframework.stereotype.Component;

import java.util.Date;

public class JwtAdapter implements JwtPort {

    private final String jwtSecret;
    private final long jwtExpirationMs;

    public JwtAdapter(String jwtSecret, long jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    @Override
    public String generateJwtToken(String email) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);  // Algoritmo HMAC con la chiave segreta

        return JWT.create()
                .withSubject(email)  // Imposta l'email come subject
                .withIssuedAt(new Date())  // Data di emissione
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))  // Data di scadenza
                .sign(algorithm);  // Firma il token
    }
}
