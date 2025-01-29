package com.librarymknw.authService.core.domain.models;

public class LoginRequest {

    private String email;
    private String password;

    // Aggiungi un costruttore che accetta email e password
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters (se necessario)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}