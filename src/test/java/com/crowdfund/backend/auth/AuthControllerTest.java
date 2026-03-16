package com.crowdfund.backend.auth;

import com.crowdfund.backend.auth.controller.AuthController;
import com.crowdfund.backend.auth.dto.LoginRequest;
import com.crowdfund.backend.auth.dto.LoginResponse;
import com.crowdfund.backend.auth.dto.RegisterRequest;
import com.crowdfund.backend.auth.security.JwtService;
import com.crowdfund.backend.auth.security.SecurityConfig;
import com.crowdfund.backend.auth.service.AuthService;
import com.crowdfund.backend.auth.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.name = "Kiran";
        request.email = "kiran@test.com";
        request.password = "123456";
        request.role = "USER";

        LoginResponse response = new LoginResponse("mock-token");

        when(authService.register(request)).thenReturn(response);

        mockMvc.perform(
                post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {

        LoginRequest request = new LoginRequest();
        request.email = "kiran@test.com";
        request.password = "123456";

        LoginResponse response = new LoginResponse("mock-token");

        when(authService.login(request)).thenReturn(response);

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestForInvalidRegisterRequest() throws Exception {

        RegisterRequest request = new RegisterRequest();

        mockMvc.perform(
                post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }
}