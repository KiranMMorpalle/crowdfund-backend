# 🏗 Backend Architecture

This document describes the **structural architecture of the backend system**, including both:

* Production architecture
* Testing architecture

The backend follows a **layered architecture** using Spring Boot.

Main architectural layers:

* Controller Layer (API endpoints)
* Service Layer (Business logic)
* Repository Layer (Database access)
* Security Layer (Authentication & Authorization)
* Exception Layer (Global error handling)
* DTO Layer (Request/Response objects)

---

# Production Architecture

The production backend code is located in:

```
src/main/java
```

### Base Package

```
com.crowdfund.backend
```

---

## Production Folder Structure

```
src/main/java/com/crowdfund/backend
│
├── auth
│   ├── controller
│   │   └── AuthController
│   │
│   ├── dto
│   │   ├── LoginRequest
│   │   ├── LoginResponse
│   │   └── RegisterRequest
│   │
│   ├── service
│   │   ├── AuthService
│   │   └── AuthServiceImpl
│   │
│   └── security
│       ├── JwtService
│       ├── JwtAuthenticationFilter
│       ├── SecurityConfig
│       └── CustomUserDetailsService
│
├── campaign
│   ├── controller
│   ├── service
│   ├── repository
│   └── domain
│
├── donation
│   ├── controller
│   ├── service
│   ├── repository
│   └── domain
│
├── payment
│   ├── controller
│   ├── service
│   └── integration
│
├── user
│   ├── domain
│   ├── repository
│   └── service
│
├── common
│   ├── exception
│   │   ├── GlobalExceptionHandler
│   │   ├── ResourceNotFoundException
│   │   ├── BusinessValidationException
│   │   └── UnauthorizedOperationException
│   │
│   └── response
│       └── ErrorResponse
│
└── BackendApplication
```

---

# Production Layer Responsibilities

### Controller Layer

Handles incoming HTTP requests.

Examples:

```
AuthController
CampaignController
DonationController
```

Responsibilities:

* Define REST endpoints
* Validate request data
* Call service layer
* Return API responses

---

### Service Layer

Contains **core business logic**.

Examples:

```
AuthServiceImpl
CampaignServiceImpl
DonationServiceImpl
```

Responsibilities:

* Business rules
* Data validation
* Transaction logic
* Security checks

---

### Repository Layer

Handles database communication using Spring Data JPA.

Examples:

```
UserRepository
CampaignRepository
DonationRepository
```

Responsibilities:

* CRUD operations
* Database queries
* Entity persistence

---

### Security Layer

Responsible for authentication and authorization.

Components:

```
JwtService
JwtAuthenticationFilter
SecurityConfig
CustomUserDetailsService
```

Responsibilities:

* JWT token generation
* JWT token validation
* Spring Security configuration
* User authentication

---

### Exception Handling Layer

Centralized error management.

```
GlobalExceptionHandler
```

Handles:

* Validation errors
* Business errors
* Resource not found errors
* Unauthorized access
* Unexpected server errors

---

# Testing Architecture

Testing code is located in:

```
src/test/java
```

Testing follows a **mirrored package structure** of the production code.

This makes tests easy to maintain and locate.

---

## Testing Folder Structure

```
src/test/java/com/crowdfund/backend
│
├── auth
│   ├── AuthControllerTest
│   └── AuthServiceTest
│
├── campaign
│   ├── CampaignControllerTest
│   └── CampaignServiceTest
│
├── donation
│   ├── DonationControllerTest
│   └── DonationServiceTest
│
└── payment
    └── PaymentServiceTest
```

---

# Types of Tests Implemented

Two major testing types are used.

---

## Service Layer Tests

Example:

```
AuthServiceTest
```

Purpose:

Test **business logic independently**.

Tools used:

* JUnit 5
* Mockito

Characteristics:

* Dependencies mocked
* Database not required
* Faster execution

Example scenarios tested:

```
User registration success
Duplicate email validation
Successful login
User not found during login
```

---

## Controller Layer Tests

Example:

```
AuthControllerTest
```

Purpose:

Test **API endpoints behavior**.

Tools used:

* MockMvc
* Spring Boot Test
* Mockito

Characteristics:

* Simulates HTTP requests
* Validates response status codes
* Validates request validation

Example scenarios tested:

```
Successful user registration
Successful login
Invalid registration request
```

---

# Test Execution Strategy

Tests are executed using:

```
JUnit 5
```

Run methods:

IDE execution:

```
Run Test
Debug Test
```

Command line execution:

```
./mvnw test
```

---

# Key Testing Principles Used

The project follows these testing principles:

* Unit testing for business logic
* Controller testing for API endpoints
* Dependency mocking for isolation
* Validation testing for request errors
* HTTP status verification

---

# Architecture Summary

Production system uses a **layered modular architecture**.

Key benefits:

* Clear separation of responsibilities
* Easier maintenance
* Independent testing of layers
* Scalable module structure
* Improved code readability

Testing architecture mirrors the production structure to ensure **consistent and maintainable backend testing.**
