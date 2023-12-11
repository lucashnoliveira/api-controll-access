package com.vicariusapi.repository.impl.user;

import com.vicariusapi.model.User;
import com.vicariusapi.repository.interfaces.user.UserRepositoryElastic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryElasticImpl implements UserRepositoryElastic {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }
}
