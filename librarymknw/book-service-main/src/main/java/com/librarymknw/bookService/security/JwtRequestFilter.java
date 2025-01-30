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
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "mySecretKey"; //hardcodata da mettere in un .env

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestURI = request.getRequestURI();

        // Applicare il filtro solo per richieste che richiedono il token (ad esempio, POST, PUT, DELETE)
        if (requestURI.startsWith("/library/") && !requestURI.equals("/library/findByString")) {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token mancante o non valido");
                return; // Blocca la richiesta
            }

            String token = authorizationHeader.substring(7);
            try {
                DecodedJWT decodedJWT = verifyToken(token);
                String username = decodedJWT.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = new User(username, "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (JWTVerificationException e) {
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
