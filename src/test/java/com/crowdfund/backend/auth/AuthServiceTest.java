package com.crowdfund.backend.auth;

import com.crowdfund.backend.auth.dto.LoginRequest;
import com.crowdfund.backend.auth.dto.LoginResponse;
import com.crowdfund.backend.auth.dto.RegisterRequest;
import com.crowdfund.backend.auth.security.JwtService;
import com.crowdfund.backend.auth.service.AuthServiceImpl;
import com.crowdfund.backend.user.domain.Role;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setup() {

        registerRequest = new RegisterRequest();
        registerRequest.name = "Kiran";
        registerRequest.email = "kiran@test.com";
        registerRequest.password = "123456";
        registerRequest.role = "USER";

        loginRequest = new LoginRequest();
        loginRequest.email = "kiran@test.com";
        loginRequest.password = "123456";

        user = new User();
        user.setName("Kiran");
        user.setEmail("kiran@test.com");
        user.setPasswordHash("encodedPassword");
        user.setRole(Role.USER);
    }

    @Test
    void shouldRegisterUserSuccessfully() {

        when(userRepository.findByEmail(registerRequest.email))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(registerRequest.password))
                .thenReturn("encodedPassword");

        when(jwtService.generateToken(anyString(), anyString()))
                .thenReturn("mocked-jwt-token");

        LoginResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.token);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowErrorWhenEmailAlreadyExists() {

        when(userRepository.findByEmail(registerRequest.email))
                .thenReturn(Optional.of(user));

        RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        authService.register(registerRequest));

        assertEquals("Email already registered", exception.getMessage());
    }

    @Test
    void shouldLoginSuccessfully() {

        when(userRepository.findByEmail(loginRequest.email))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(anyString(), anyString()))
                .thenReturn("mocked-jwt-token");

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.token);

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );
    }

    @Test
    void shouldThrowErrorWhenUserNotFoundDuringLogin() {

        when(userRepository.findByEmail(loginRequest.email))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        authService.login(loginRequest));

        assertEquals("Invalid email or password", exception.getMessage());
    }
}