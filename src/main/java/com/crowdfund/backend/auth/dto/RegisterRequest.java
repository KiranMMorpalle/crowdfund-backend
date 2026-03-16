package com.crowdfund.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "Name is required")
    public String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    public String email;

    @NotBlank(message = "Password is required")
    public String password;

    @NotBlank(message = "Role is required")
    public String role;
}