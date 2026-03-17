package com.crowdfund.backend.user.controller;

import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(
                request.name,
                request.email,
                request.password,
                request.role
        );
    }

    // ✅ KEEP THIS INSIDE CONTROLLER (NO NEW FILE NEEDED)
    public static class CreateUserRequest {

        @NotBlank
        public String name;

        @Email
        @NotBlank
        public String email;

        @NotBlank
        public String password;

        public String role;
    }
}










//
//package com.crowdfund.backend.user.controller;
//
//import com.crowdfund.backend.user.domain.User;
//import com.crowdfund.backend.user.service.UserService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/users")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    public static class CreateUserRequest {
//        public String name;
//        public String email;
//        public String password;
//        public String role;
//    }
//
//    @PostMapping
//    public User createUser(@RequestBody CreateUserRequest request) {
//
//        return userService.createUser(
//                request.name,
//                request.email,
//                request.password,
//                request.role != null ? request.role : "USER"
//        );
//    }
//}