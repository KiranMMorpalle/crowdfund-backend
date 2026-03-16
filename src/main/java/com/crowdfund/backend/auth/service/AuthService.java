package com.crowdfund.backend.auth.service;

import com.crowdfund.backend.auth.dto.LoginRequest;
import com.crowdfund.backend.auth.dto.LoginResponse;
import com.crowdfund.backend.auth.dto.RegisterRequest;

public interface AuthService {

    LoginResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}