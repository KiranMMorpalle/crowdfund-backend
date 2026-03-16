package com.crowdfund.backend.auth.controller;

import com.crowdfund.backend.auth.dto.LoginRequest;
import com.crowdfund.backend.auth.dto.LoginResponse;
import com.crowdfund.backend.auth.dto.RegisterRequest;
import com.crowdfund.backend.auth.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public LoginResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}



//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public LoginResponse register(@RequestBody RegisterRequest request)
//    {
//        if (userRepository.findByEmail(request.email).isPresent()) {
//            throw new RuntimeException("Email already registered");
//        }
//
//        User user = new User();
////        user.setId(UUID.randomUUID());
//        user.setName(request.name);
//        user.setEmail(request.email);
//        user.setPasswordHash(passwordEncoder.encode(request.password));
//        user.setRole(Role.valueOf(request.role));
//
//        userRepository.save(user);
//
//        String token = jwtService.generateToken(
//                user.getEmail(),
//                user.getRole().name()
//        );
//        return new LoginResponse(token);
//    }
//
//
//
//    @PostMapping("/login")
//    public LoginResponse login(@RequestBody LoginRequest request) {
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email,
//                        request.password
//                )
//        );
//
//        var user = userRepository
//                .findByEmail(request.email)
//                .orElseThrow();
//
//        String token = jwtService.generateToken(
//                user.getEmail(),
//                user.getRole().name()
//        );
//
//        return new LoginResponse(token);
//    }
// }