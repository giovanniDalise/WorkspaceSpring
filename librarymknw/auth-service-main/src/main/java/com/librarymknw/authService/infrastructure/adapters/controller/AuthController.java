package com.librarymknw.authService.infrastructure.adapters.controller;

import com.librarymknw.authService.core.domain.services.AuthenticationService;
import com.librarymknw.authService.core.domain.models.LoginRequest;
import com.librarymknw.authService.core.domain.models.AuthResponse;
import com.librarymknw.authService.core.ports.AuthenticationServicePort;
import com.librarymknw.authService.core.ports.JwtPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationServicePort authenticationService;
    private final JwtPort jwtPort;  // Aggiunto per la generazione del JWT

    // Modifica il costruttore per iniettare anche JwtPort
    public AuthController(AuthenticationServicePort authenticationService, JwtPort jwtPort) {
        this.authenticationService = authenticationService;
        this.jwtPort = jwtPort;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Verifica se le credenziali sono vuote
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o password non validi");
        }

        // Usa il metodo authenticate del servizio
        AuthResponse authResponse = authenticationService.authenticate(request);

        if (authResponse != null) {
            return ResponseEntity.ok(authResponse);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o password non validi");
    }

}
