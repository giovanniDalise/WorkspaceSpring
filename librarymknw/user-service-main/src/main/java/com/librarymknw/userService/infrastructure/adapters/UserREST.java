package com.librarymknw.userService.infrastructure.adapters;

import com.librarymknw.userService.core.domain.models.User;
import com.librarymknw.userService.core.ports.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/users")
public class UserREST {

    private final UserRepositoryPort userRepositoryPort;

    @Autowired
    public UserREST(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    // Endpoint per ottenere tutti gli utenti
    @GetMapping
    public List<User> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    // Endpoint per ottenere un utente per id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepositoryPort.findById(id);
    }

    // Endpoint per creare un nuovo utente
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userRepositoryPort.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            // Log dell'errore
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la creazione dell'utente: " + e.getMessage());
        }
    }


    // Endpoint per aggiornare un utente esistente
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);  // Assicurati che l'id venga settato
        userRepositoryPort.update(user);
    }

    // Endpoint per eliminare un utente
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userRepositoryPort.delete(id);
    }
}