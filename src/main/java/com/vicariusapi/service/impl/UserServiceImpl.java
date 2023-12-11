package com.vicariusapi.service.impl;

import com.vicariusapi.model.User;
import com.vicariusapi.repository.RepositoryFactory;
import com.vicariusapi.repository.interfaces.user.UserRepositoryStrategy;
import com.vicariusapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryStrategy userRepositoryStrategy;

    @Autowired
    public UserServiceImpl(RepositoryFactory repositoryFactory) {
        this.userRepositoryStrategy = repositoryFactory.getUserRepository();
    }

    @Override
    public List<User> listAllUsers() {
       return userRepositoryStrategy.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepositoryStrategy.findById(id);
    }

    @Override
    public User addUser(User user) {
        return userRepositoryStrategy.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepositoryStrategy.save(user);
    }

    @Override
    public void deteleUserById(Long id) {
        userRepositoryStrategy.deleteById(id);
    }
}
