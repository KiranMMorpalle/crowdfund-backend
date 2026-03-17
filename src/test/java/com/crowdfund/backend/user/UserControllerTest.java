package com.crowdfund.backend.user;

import com.crowdfund.backend.auth.security.JwtAuthenticationFilter;
import com.crowdfund.backend.user.controller.UserController;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false) // ✅ IMPORTANT: disables security filters
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        // Arrange
        User user = new User();
        user.setName("John");
        user.setEmail("test@example.com");

        when(userService.createUser(
                "John",
                "test@example.com",
                "password123",
                "USER"
        )).thenReturn(user);

        String requestJson = """
        {
            "name": "John",
            "email": "test@example.com",
            "password": "password123",
            "role": "USER"
        }
        """;

        // Act + Assert
        mockMvc.perform(post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void shouldFailWhenInvalidInput() throws Exception {
        String requestJson = """
        {
            "name": "",
            "email": "invalid-email",
            "password": ""
        }
        """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}