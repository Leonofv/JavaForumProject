package com.example.ForumProject.service;

import com.example.ForumProject.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    User validateAndGetUserByUsername(String username);

    User saveUser(User user);

    User getUserById(Long id);
}
