package com.ask0n.cloudvault.services;

import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.exceptions.NotFoundException;
import com.ask0n.cloudvault.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User: " + username + " not found"));
    }

    public User findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Bad credentials");
        return user;
    }
}
