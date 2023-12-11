package com.vicariusapi.repository.interfaces.user;

import com.vicariusapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryStrategy {

    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
