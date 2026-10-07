package com.project.EasyExpense.service.impl;

import com.project.EasyExpense.exception.EmailAlreadyExistsException;
import com.project.EasyExpense.exception.InvalidCredentialsException;
import com.project.EasyExpense.exception.UsernameAlreadyExistsException;
import com.project.EasyExpense.model.User;
import com.project.EasyExpense.repository.UserRepository;
import com.project.EasyExpense.security.JwtService;
import com.project.EasyExpense.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public User signup(User user) {

        if (userRepository.existsByUsername(
                user.getUsername())) {

            throw new UsernameAlreadyExistsException(
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmail(
                user.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "Email already registered"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);
    }

    @Override
    public User login(
            String email,
            String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        ));

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        return user;
    }
}