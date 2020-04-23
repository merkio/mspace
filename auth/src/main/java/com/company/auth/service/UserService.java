package com.company.auth.service;

import com.company.auth.domain.User;
import com.company.auth.exception.UserAlreadyExistsException;
import com.company.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(@NotNull User user) {
        userRepository.findByUsername(user.getUsername())
            .ifPresent(u -> {
                throw new UserAlreadyExistsException("User already exists: [{}]", user.getUsername());
            });

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        log.info("User has been created: {}", savedUser.getUsername());

        return savedUser;
    }
}
