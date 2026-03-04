package com.crowdfund.backend.user.service;

import com.crowdfund.backend.common.exception.BusinessValidationException;
import com.crowdfund.backend.user.domain.Role;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name,
                           String email,
                           String password,
                           String role) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new BusinessValidationException("Email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        // 🔐 Hash password properly
        String hashedPassword = passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);

        if (role != null) {
            user.setRole(Role.valueOf(role));
        }

        return userRepository.save(user);
    }
}