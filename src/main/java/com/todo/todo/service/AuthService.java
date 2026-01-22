package com.todo.todo.service;

import com.todo.todo.dto.LoginRequest;
import com.todo.todo.dto.RegisterRequest;
import com.todo.todo.entity.User;
import com.todo.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

//Service for handling user authentication
@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request){
        //check if email is registered
        userRepository.findByEmail(request.getEmail())
                // If a user is found, throw an exception to prevent duplicate accounts
                .ifPresent(u -> {
                    throw new RuntimeException("Email already in use");
                });

        // Create and save new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    // Authenticate user login
    public User login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify password matches
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
}
