package com.vicariusapi.repository.interfaces.user;

import com.vicariusapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryMySQL extends JpaRepository<User, Long>, UserRepositoryStrategy {
}
