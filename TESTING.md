# 🧪 Backend Testing Documentation

This document tracks all **unit and controller testing implementations** across modules in the backend.

Testing is implemented using:

* **JUnit 5**
* **Mockito**
* **Spring Boot Test**
* **MockMvc**

Purpose of testing:

* Verify business logic correctness
* Ensure API endpoints behave correctly
* Detect bugs early in development
* Maintain stable backend behavior during refactoring

---

# Auth Module Testing

## Overview

Testing was implemented for the **authentication module** to verify:

* User registration
* User login
* Error handling
* Validation handling

Two types of tests were created:

| Test Type            | File                 |
| -------------------- | -------------------- |
| Service Layer Tests  | `AuthServiceTest`    |
| Controller/API Tests | `AuthControllerTest` |

---

# Service Layer Tests

**File**

```
AuthServiceTest
```

Purpose:

Test the **business logic inside AuthServiceImpl** without running the full Spring Boot application.

Dependencies mocked using **Mockito**.

### Tested Scenarios

1️⃣ **User Registration Success**

Expected result:

* User does not already exist
* Password is encoded
* User is saved successfully
* JWT token is generated

Test case:

```
shouldRegisterUserSuccessfully
```

---

2️⃣ **Duplicate Email Registration**

Expected result:

* Registration fails if email already exists

Test case:

```
shouldThrowErrorWhenEmailAlreadyExists
```

---

3️⃣ **Successful Login**

Expected result:

* User email exists
* Password matches
* JWT token generated

Test case:

```
shouldLoginSuccessfully
```

---

4️⃣ **Login Failure (User Not Found)**

Expected result:

* Login fails when email does not exist

Test case:

```
shouldThrowErrorWhenUserNotFoundDuringLogin
```

---

# Controller Layer Tests

**File**

```
AuthControllerTest
```

Purpose:

Test **REST API endpoints** using `MockMvc`.

This ensures:

* HTTP endpoints behave correctly
* Response status codes are correct
* Request validation works properly

Security filters were disabled in tests to isolate controller behavior.

```
@AutoConfigureMockMvc(addFilters = false)
```

Security configuration was imported:

```
@Import(SecurityConfig.class)
```

---

# Tested API Endpoints

### Register Endpoint

```
POST /api/v1/auth/register
```

---

### Login Endpoint

```
POST /api/v1/auth/login
```

---

# Controller Test Cases

1️⃣ **Successful User Registration**

Expected result:

```
HTTP 200 OK
```

Test case:

```
shouldRegisterUserSuccessfully
```

---

2️⃣ **Successful Login**

Expected result:

```
HTTP 200 OK
```

Test case:

```
shouldLoginSuccessfully
```

---

3️⃣ **Invalid Registration Request**

Scenario:

Empty request body or invalid fields.

Expected result:

```
HTTP 400 BAD_REQUEST
```

Test case:

```
shouldReturnBadRequestForInvalidRegisterRequest
```

---

# Validation Improvements Implemented

To support controller testing, validation was added to request DTOs.

Example validations added:

* Name → required
* Email → required and valid format
* Password → required
* Role → required

Validation annotations used:

```
@NotBlank
@Email
```

---

# Global Exception Handling Improvement

A new handler was added to support validation errors:

```
MethodArgumentNotValidException
```

Expected behavior:

```
Invalid request → HTTP 400
```

Previously:

```
Validation error → HTTP 500
```

Now corrected through the GlobalExceptionHandler.

---

# Dependencies Used for Testing

```
spring-boot-starter-test
spring-boot-starter-validation
mockito-core
mockito-junit-jupiter
```

---

# Final Test Coverage (Auth Module)

| Layer      | Test File          | Status    |
| ---------- | ------------------ | --------- |
| Service    | AuthServiceTest    | ✅ Passing |
| Controller | AuthControllerTest | ✅ Passing |

Total implemented test scenarios:

```
5 test cases
```

---

# Summary

Authentication module now includes:

* Service layer unit tests
* Controller API tests
* Request validation
* Proper error handling
* Stable API status verification

This ensures the **authentication system behaves reliably and remains safe during future code changes.**

---

Append this **Auth Module Testing section** to your `README.md`.

```markdown
## 🧪 Testing — Auth Module

JUnit testing has been implemented for the **Authentication module** to verify both business logic and API behavior.

### Testing Stack

- **JUnit 5** – Unit testing framework
- **Mockito** – Mocking dependencies
- **Spring Boot Test**
- **MockMvc** – API controller testing

---

### Implemented Test Files

Service layer testing:

```

AuthServiceTest

```

Controller layer testing:

```

AuthControllerTest

```

---

### Service Layer Test Coverage

The following scenarios are tested:

- User registration success
- Duplicate email registration failure
- Successful user login
- Login failure when user is not found

Purpose:

- Verify business logic correctness
- Ensure service methods behave as expected
- Mock repository and security dependencies

---

### Controller Layer Test Coverage

API endpoints tested:

```

POST /api/v1/auth/register
POST /api/v1/auth/login

```

Test scenarios:

- Successful user registration
- Successful user login
- Invalid registration request (validation error)

Expected HTTP responses verified:

```

200 OK
400 BAD_REQUEST

```

---

### Validation Improvements

To support API validation testing, request validation was added.

DTO validations implemented:

- `@NotBlank` for required fields
- `@Email` for email format validation

Example fields validated:

- name
- email
- password
- role

---

### Global Exception Handling Update

Validation exception handling was added to the `GlobalExceptionHandler`.

Handled exception:

```

MethodArgumentNotValidException

```

This ensures invalid requests correctly return:

```

HTTP 400 BAD_REQUEST

```

instead of

```

HTTP 500 INTERNAL_SERVER_ERROR

```

---

### Test Status

| Layer | Test File | Status |
|------|------|------|
| Service | AuthServiceTest | ✅ Passing |
| Controller | AuthControllerTest | ✅ Passing |

Total implemented test cases:

```

5 test scenarios

```

---

Future testing will be implemented for:

- Campaign Module
- Donation Module
- Payment Module
- User Module
```



---

## ✅ Campaign Module Testing

## Overview

Testing implemented for **Campaign + Admin Campaign module** covering:

- Business logic validation
- Public API behavior
- Admin approval/rejection flows
- Security handling

---

## Test Files

Service Layer:

```

CampaignServiceTest

```

Controller Layer:

```

CampaignControllerTest
AdminCampaignControllerTest

```

---

## Service Layer Coverage

- Campaign creation
- Campaign update
- Campaign deletion
- Campaign search (pagination, filters)
- Approval logic
- Rejection logic
- Invalid state handling

---

## Controller Layer Coverage

### Public APIs

```

GET /api/v1/campaigns
GET /api/v1/campaigns/{id}
POST /api/v1/campaigns
PUT /api/v1/campaigns/{id}
DELETE /api/v1/campaigns/{id}

```

### Admin APIs

```

PUT /api/v1/admin/campaigns/{id}/approve
PUT /api/v1/admin/campaigns/{id}/reject

```

---

## Key Fixes During Testing

- Fixed incorrect test methods (removed non-existing service calls)
- Fixed `NullPointerException` due to missing `Authentication`
- Added `@WithMockUser` for security context injection
- Fixed `403 Forbidden` using `.with(csrf())` for PUT APIs
- Mocked security dependencies (`JwtService`, `CustomUserDetailsService`)
- Ensured test structure matches actual controller endpoints

---

## Security Handling in Tests

- Role-based access simulated using:

```

@WithMockUser(roles = "ADMIN")

```

- CSRF added for state-changing requests:

```

.with(csrf())

```

---

## Test Status

| Layer      | Test File                        | Status    |
| ---------- | ------------------------------- | --------- |
| Service    | CampaignServiceTest             | ✅ Passing |
| Controller | CampaignControllerTest          | ✅ Passing |
| Controller | AdminCampaignControllerTest     | ✅ Passing |

---

## Summary

Campaign module now includes:

- Service unit tests
- Public controller tests
- Admin controller tests
- Security-aware testing
- Stable API validation

Ensures reliable campaign lifecycle handling and admin moderation.

---

## ✅Donation Module Testing

## Overview

Testing implemented for the **Donation module** to verify:

- Donation creation flow
- Donation confirmation flow
- Validation handling
- Exception handling

---

## Test Files

Controller Layer:

```

DonationControllerTest

```

---

## Controller Layer Coverage

### Donation API

```

POST /api/v1/campaigns/{id}/donate

```

### Confirmation API

```

POST /api/v1/campaigns/donations/{id}/confirm

```

---

## Test Scenarios

- Successful donation → `200 OK`
- Successful donation confirmation → `200 OK`
- Invalid request (missing amount) → `400 BAD_REQUEST`
- Service exception → `500 INTERNAL_SERVER_ERROR`

---

## Security Handling in Tests

To isolate controller behavior:

- Disabled security filters:

```

@AutoConfigureMockMvc(addFilters = false)

```

- Mocked required security dependencies:

```

@MockBean JwtAuthenticationFilter
@MockBean JwtService

```

This prevents ApplicationContext loading failures during testing.

---

## Validation Handling

- DTO validation applied for donation request
- Invalid input correctly returns:

```

HTTP 400 BAD_REQUEST

```

---

## Exception Handling

- Service-level exceptions mapped to:

```

HTTP 500 INTERNAL_SERVER_ERROR

(Handled via GlobalExceptionHandler)

---

## Test Status

| Layer      | Test File               | Status    |
| ---------- | ---------------------- | --------- |
| Controller | DonationControllerTest | ✅ Passing |

---

## Summary

Donation module testing now includes:

- Controller-level API testing
- Validation testing
- Exception handling verification
- Security-isolated test execution

Ensures reliable donation flow and stable API behavior.
```

---

## 🔥Payment Module Testing

## Overview

Testing implemented for the **Payment module** to verify:

- Payment order creation flow
- Payment verification logic
- Webhook-based payment confirmation
- Donation status update after payment
- Error handling and edge cases

Two types of tests were created:

| Test Type            | File                              |
| -------------------- | --------------------------------- |
| Service Layer Tests  | `PaymentServiceTest`              |
| Controller/API Tests | `PaymentControllerTest`           |
| Controller/API Tests | `RazorpayWebhookControllerTest`   |

---

# Service Layer Tests

**File**

```

PaymentServiceTest

```

Purpose:

Test the **business logic inside PaymentServiceImpl** without running the full Spring Boot application.

Dependencies mocked using **Mockito**.

---

## Tested Scenarios

1️⃣ **Payment Verification Success**

Expected result:

- Donation exists
- Payment exists
- Payment status updated to `SUCCESS`
- Donation status updated to `SUCCESS`
- Razorpay payment ID stored

---

2️⃣ **Donation Not Found**

Expected result:

- Exception thrown when donation does not exist

---

3️⃣ **Payment Not Found**

Expected result:

- Exception thrown when payment is not found for donation

---

## Special Handling

Since `Donation` entity does not expose setters or builder:

- Used:

```

ReflectionTestUtils.setField()

```

This allows setting private fields safely in tests without modifying production code.

---

# Controller Layer Tests

## 1. PaymentControllerTest

**File**

```

PaymentControllerTest

```

Purpose:

Test **payment-related REST APIs** using `MockMvc`.

---

### Tested API Endpoints

```

POST /api/v1/payments/order/{donationId}
POST /api/v1/payments/verify

```

---

### Test Scenarios

- Successful order creation → `HTTP 200 OK`
- Successful payment verification → `HTTP 200 OK`

---

## 2. RazorpayWebhookControllerTest

**File**

```

RazorpayWebhookControllerTest

```

Purpose:

Test **webhook endpoint handling** for Razorpay events.

---

### Tested API Endpoint

```

POST /api/v1/payments/webhook

```

---

### Test Scenarios

- Valid webhook payload processed successfully → `HTTP 200 OK`

---

# Security Handling in Tests

To isolate controller behavior and prevent ApplicationContext failures:

### Disabled Security Filters

```

@AutoConfigureMockMvc(addFilters = false)

```

---

### Excluded JWT Filter

```

excludeFilters = @ComponentScan.Filter(
type = FilterType.ASSIGNABLE_TYPE,
classes = JwtAuthenticationFilter.class
)

```

---

### Reason

- Prevents loading full Spring Security context
- Avoids dependency issues (`JwtService not found`)
- Ensures fast and isolated controller testing

---

# Validation & Exception Handling

- Controller tests verify correct HTTP responses
- Exception scenarios return appropriate status codes
- Business logic exceptions handled at service layer

---

# Dependencies Used

```

spring-boot-starter-test
mockito-core
mockito-junit-jupiter
spring-test

```

---

# Final Test Coverage (Payment Module)

| Layer      | Test File                          | Status    |
| ---------- | ---------------------------------- | --------- |
| Service    | PaymentServiceTest                 | ✅ Passing |
| Controller | PaymentControllerTest              | ✅ Passing |
| Controller | RazorpayWebhookControllerTest      | ✅ Passing |

Total implemented test scenarios:

```

5+ test cases

```

---

# Summary

Payment module now includes:

- Service layer unit testing
- Controller API testing
- Webhook endpoint testing
- Secure test isolation from Spring Security
- Real-world payment flow validation

This ensures the **payment system is reliable, testable, and production-ready**, especially for critical financial workflows.

```



---
# Future Testing Expansion

Upcoming modules that will receive testing:

* Campaign Module
* Donation Module
* Payment Module
* User Module
* Admin Module
