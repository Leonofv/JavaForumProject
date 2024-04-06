package com.example.ForumProject.service;

import com.example.ForumProject.exception.MessageNotFoundException;
import com.example.ForumProject.model.User;
import com.example.ForumProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new MessageNotFoundException("User with this username not found"));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }
}
