package com.librarymknw.authService.infrastructure.adapters.repository;

import com.librarymknw.authService.core.ports.AuthenticationRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryAdapter implements AuthenticationRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public AuthRepositoryAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean checkUserCredentials(String email, String password) {
        String sql = "SELECT COUNT(1) FROM user WHERE email = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, Integer.class);
        return count > 0; // Se il conteggio è maggiore di 0, significa che le credenziali sono corrette
    }

    @Override
    // Recupero ruolo dell'utente
    public String getUserRole(String email) {
        String sql = "SELECT r.name FROM user u " +
                "JOIN role r ON u.role = r.role_id " +
                "WHERE u.email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
    }
}
