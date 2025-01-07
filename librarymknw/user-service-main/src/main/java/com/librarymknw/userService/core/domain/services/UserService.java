package com.librarymknw.userService.core.domain.services;

import com.librarymknw.userService.core.domain.models.User;
import com.librarymknw.userService.core.ports.UserRepositoryPort;
import com.librarymknw.userService.core.ports.UserServicePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServicePort {

    private final UserRepositoryPort repository;

    public UserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id);
    }

    public void createUser(User user) {
        repository.save(user);
    }

    public void updateUser(Long id,User user) {
        repository.update(user);
    }

    public void deleteUser(Long id) {
        repository.delete(id);
    }
}
