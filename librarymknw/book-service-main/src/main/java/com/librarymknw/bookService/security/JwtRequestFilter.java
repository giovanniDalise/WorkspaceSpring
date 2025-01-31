package com.librarymknw.bookService.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "mySecretKey"; //hardcodata da mettere in un .env

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestURI = request.getRequestURI();

        // Log per vedere la richiesta URI e l'header Authorization
        System.out.println("Richiesta URI: " + requestURI);
        System.out.println("Header Authorization: " + authorizationHeader);

        // (filtro per token non valido nel caso dell'admin))
        if (requestURI.startsWith("/library/") && !requestURI.equals("/library/findByString")) {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                System.out.println("Token mancante o non valido");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token mancante o non valido");
                return; // Blocca la richiesta
            }

            String token = authorizationHeader.substring(7);
            try {
                DecodedJWT decodedJWT = verifyToken(token);
                String username = decodedJWT.getSubject();

                // Log del token decodificato e del subject
                System.out.println("Token decodificato: " + decodedJWT.getPayload());
                System.out.println("Username decodificato: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = new User(username, "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Log dell'autenticazione settata
                    System.out.println("Autenticazione settata per l'utente: " + username);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (JWTVerificationException e) {
                System.out.println("Errore nella verifica del token: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token non valido: " + e.getMessage());
                return; // Blocca la richiesta
            }
        }

        chain.doFilter(request, response);
    }

    private DecodedJWT verifyToken(String token) throws JWTVerificationException {
        System.out.println("Verifica del token con la SECRET_KEY: " + SECRET_KEY);
        JWTVerifier verifier = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC512(SECRET_KEY))
                .build();
        return verifier.verify(token);
    }
}
