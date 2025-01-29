package com.librarymknw.userService.core.ports;

import com.librarymknw.userService.core.domain.models.User;
import java.util.List;

public interface UserRepositoryPort {
    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void update(User user);
    void delete(Long id);
    }
