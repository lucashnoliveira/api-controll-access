package com.vicariusapi.service.interfaces;

import com.vicariusapi.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    /**
     * Get all the users from the current database
     * @return list of users
     */
    public List<User> listAllUsers();

    /**
     * Get a user by id, if exists;
     * @return Optional from a User
     */
    public Optional<User> getUserById(Long id);

    /**
     * Insert a user into the current database;
     * @param user User to be inserted into the database;
     * @return User that was inserted into the database;
     */
    public User addUser(User user);

    /**
     * Update a user
     * @param user User to be updated into the database;
     * @return User that was updated into the database;
     */
    public User updateUser(User user);

    /**
     * Delete a user
     * @param id User id to be deleted;
     * @return
     */
    public void deteleUserById(Long id);
}
