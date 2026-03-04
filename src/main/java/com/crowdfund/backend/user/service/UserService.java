package com.crowdfund.backend.user.service;

import com.crowdfund.backend.user.domain.User;

public interface UserService {

    User createUser(String name,
                    String email,
                    String password,
                    String role);
}