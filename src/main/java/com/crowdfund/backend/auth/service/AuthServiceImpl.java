package com.crowdfund.backend.auth.service;

import com.crowdfund.backend.auth.dto.*;
import com.crowdfund.backend.auth.security.JwtService;
import com.crowdfund.backend.user.domain.Role;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPasswordHash(passwordEncoder.encode(request.password));
        user.setRole(Role.valueOf(request.role));

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new LoginResponse(token);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email,
                        request.password
                )
        );

//        var user = userRepository
//                .findByEmail(request.email)
//                .orElseThrow();

        var user = userRepository
                .findByEmail(request.email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new LoginResponse(token);
    }
}