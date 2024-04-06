package com.example.ForumProject.controller;

import com.example.ForumProject.config.WebSecurityConfig;
import com.example.ForumProject.dto.AuthResponse;
import com.example.ForumProject.dto.UserRequest;
import com.example.ForumProject.dto.LoginRequest;
import com.example.ForumProject.model.User;
import com.example.ForumProject.security.TokenProvider;
import com.example.ForumProject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

    @PostMapping("/registrate")
    public void createUser(@Valid @RequestBody UserRequest userRequest) {
        if (userService.hasUserWithUsername(userRequest.getUsername())) {
            log.info(String.format("Username %s already been used", userRequest.getUsername()));
        }
        userService.saveUser(mapCreateUserRequest(userRequest));
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    private User mapCreateUserRequest(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setFatherName(userRequest.getFatherName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(WebSecurityConfig.USER);
        return user;
    }
}
