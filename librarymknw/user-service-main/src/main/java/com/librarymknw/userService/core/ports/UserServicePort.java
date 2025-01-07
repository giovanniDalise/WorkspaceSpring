package com.librarymknw.userService.core.ports;

import com.librarymknw.userService.core.domain.models.User;

import java.util.List;

public interface UserServicePort {
    List <User> getAllUsers();
    User getUserById(Long id);
    void createUser(User user);
    void updateUser(Long id, User user);
    void deleteUser(Long id);
}
