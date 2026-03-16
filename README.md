# Crowdfunding Backend System

A modular monolithic backend system built using Spring Boot,
designed with enterprise-grade architecture principles.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot 4
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Razorpay Payment Gateway
- JWT Authentication
- UUID-based identity
- Optimistic locking
- Role-Based Access Control

---

## 🏗 Architecture Style

- Feature-based modular structure
- Layered architecture (Controller → Service → Repository)
- UUID-based primary keys
- Optimistic locking for concurrency
- Role-Based Access Control (RBAC)
- Indexed query optimization
- Modular payment integration

---

## 📂 Project Structure

com.crowdfund.backend
│
├── campaign
│ ├── controller
│ ├── service
│ ├── repository
│ └── domain
│
├── donation
│ ├── controller
│ ├── service
│ ├── repository
│ └── domain
│
├── payment
│ ├── controller
│ ├── service
│ ├── repository
│ └── domain
│
├── auth
│
├── user
│
└── common
---

## 🧠 Design Decisions

- UUID used instead of Long for global uniqueness
- BigDecimal used for monetary calculations
- @Version used for optimistic locking
- Indexed columns for query optimization
- Clean separation of domain logic
- Feature-based modular architecture
- Secure webhook-based payment confirmation

---

# 📊 Version Milestones

| Day | Version | Description |
|----|----|----|
| Day 1 | v1.0.0 | Domain + Repository Foundation |
| Day 2 | v1.1.0 | Service Layer + Business Validation |
| Day 3 | v1.2.0 | REST Controllers |
| Day 4 | v1.3.0 | Donation + Concurrency |
| Day 5 | v2.0.0 | JWT Authentication |
| Day 6 | v2.1.0 | Payment Module + Razorpay |
| Day 7 | v3.0.0 | Deployment |

---
# 📌 Current Status

Core crowdfunding platform implemented with:

- Campaign management
- Secure donations
- Payment gateway integration
- Authentication and authorization

System supports **real-world payment flow using Razorpay webhooks.**

---
## 📌 Day 2 – Service Layer Implementation

### ✅ Implemented

- Service Layer (CampaignService + Impl)
- Business validation rules
- Role-Based Access Control (manual enforcement)
- Custom exception hierarchy
- Transaction boundaries (@Transactional)
- Optimistic locking using @Version
- UUID-based identity
- Foreign key mapping (Campaign → User)

---
### 🧠 Architectural Improvements

- Clean separation of concerns
- Controlled state transitions (PENDING → APPROVED / REJECTED)
- Indexed columns for query optimization
- Domain-driven validation at service layer

---

### 🔐 Security & Data Integrity

- NOT NULL constraints
- Foreign key constraints
- Unique email constraint
- Optimistic locking for concurrency
- Version column for concurrency safety
---

## 📌 Day 3 – REST API Stabilization

### 🎯 Objective
Convert service layer into production-grade REST APIs with:
- DTO boundary
- Global exception handling
- Proper HTTP status codes
- Public/Admin API separation
- Versioned endpoints (`/api/v1`)

---

## 🔹 Public APIs

| Method | Endpoint                                    |
|--------|---------------------------------------------|
| POST   | `/api/v1/campaigns`                         |
| GET    | `/api/v1/campaigns`                         |
| GET    | `/api/v1/campaigns?page={page}&size={size}` |
| GET    | `/api/v1/campaigns?keyword={name}`          |
| GET    | `/api/v1/campaigns?category={category}`     |
| GET    | `/api/v1/campaigns?sortDir={asc/desc}`      |
| GET    | `/api/v1/campaigns/{id}`                    |
| PUT    | `/api/v1/campaigns/{id}`                    |


---

## 🔹 Admin APIs

| Method | Endpoint |
|--------|----------|
| PUT    | `/api/v1/campaigns/{id}/approve` |
| PUT    | `/api/v1/campaigns/{id}/reject` |
| DELETE | `/api/v1/campaigns/{id}` |

(Admin actions require `adminId` query parameter – temporary until JWT integration.)

---

## 🏗 Key Improvements Implemented

- DTO Boundary Pattern (No entity exposure)
- ApiResponse wrapper for consistent JSON responses
- GlobalExceptionHandler with structured error responses
- Proper HTTP status alignment (201, 400, 403, 404)
- Role-based access control (manual RBAC)
- Status-based public filtering (only APPROVED visible)
- API versioning (`/api/v1`)
- Campaign listing supports search, category filtering, sorting, and pagination

---

## 🧪 Test Coverage (Manually Verified via Postman)

- Campaign creation → `201 CREATED`
- Admin approval → `200 OK`
- Duplicate approval → `400 BAD_REQUEST`
- Unauthorized admin action → `403 FORBIDDEN`
- Invalid data → `400 BAD_REQUEST`
- Not found → `404 NOT_FOUND`
- Campaign update → `200 OK`
- Campaign delete by admin → `200 OK`
- Campaign pagination → `200 OK`
- Campaign search by name → `200 OK`
- Campaign filter by category → `200 OK`
- Campaign sorting by target amount → `200 OK`
- Public visibility filtering verified

---

## Day 4 – Donation Module & Concurrency Control

## Overview
Implemented donation functionality with safe concurrent updates using optimistic locking.

## Features Added
- Donation entity with Campaign relationship
- Raised amount transactional update
- Optimistic locking using @Version
- Conflict handling (HTTP 409)
- Donation listing API
- Input validation for donation amount

## New APIs

### Donate to Campaign
POST http://localhost:8080/api/v1/campaigns/{id}/donate

### Get Campaign Donations
GET http://localhost:8080/api/v1/campaigns/{id}/donations

## Concurrency Handling
- Uses JPA @Version field
- Prevents lost update problem
- Returns 409 on version conflict
- Ensures financial consistency

---

# 📌 Day 5 – JWT Security

### Implemented

- JWT authentication
- Login endpoint
- Role-based access control
- Token-based API protection
- Custom user authentication service

---

# 📌 Day 6 – Payment Module

### 🎯 Objective

Implement production-style payment flow using Razorpay.

---

## Features Implemented

- Payment entity
- Payment status tracking
- Razorpay order creation
- Payment verification
- Webhook-based payment confirmation
- Donation status update after successful payment

---

## Payment Flow

Create Donation
↓
Create Razorpay Order
↓
User Payment via Razorpay
↓
Razorpay Webhook
↓
Backend verifies event
↓
Payment SUCCESS
↓
Donation SUCCESS

---

## Webhook Endpoint

POST /api/v1/payments/webhook


Triggered automatically by Razorpay when payment is captured.

---

## Security

- Webhook signature validation
- Payment status integrity
- Transactional updates
- Enum-based payment states

---

# 🧪 Manual Testing

Tested using Postman and Razorpay test mode.

Verified scenarios:

- Campaign creation
- Donation creation
- Razorpay order generation
- Payment success webhook
- Donation status update
- Concurrency safety

---

# 🐳 Docker Deployment

## Overview

The backend can be containerized using Docker to ensure consistent execution across environments.

The Docker container packages the Spring Boot application along with its runtime dependencies.

---

## Build Application

Generate the executable Spring Boot JAR.

```

./mvnw clean package

```

Generated artifact:

```

target/*.jar

```

---

## Build Docker Image

Create the Docker image from the Dockerfile.

```

docker build -t crowdfunding-app .

```

This packages the Spring Boot application into a container image.

---

## Run Docker Container

Start the application container.

```

docker run -p 8080:8080 crowdfunding-app

```

Application will be accessible at:

```

[http://localhost:8080](http://localhost:8080)

```

---

## Database Configuration for Docker

Since PostgreSQL runs on the host machine, the datasource URL must allow container access.

```

spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/crowdfund

```

---

## PostgreSQL Configuration

Docker containers connect from a different network interface, so PostgreSQL must allow external connections.

Edit the PostgreSQL configuration file:

```

C:\Program Files\PostgreSQL\18\data\pg_hba.conf

```

Add the following line:

```

host all all 0.0.0.0/0 md5

```

Restart the PostgreSQL service after updating the configuration.

---

## Inspect Docker Image (Optional)

Open container shell:

```

docker run -it --entrypoint sh crowdfunding-app

```

Check files inside container:

```

ls

```

Expected output:

```

app.jar

```

To inspect JAR structure:

```

jar tf app.jar

```

This displays:

- `BOOT-INF/classes` → application classes
- `BOOT-INF/lib` → dependencies


---

## 📄 Redis Caching

### Overview
Redis caching is implemented to improve performance by reducing repeated database queries for frequently accessed campaign data.

The cache layer stores responses in Redis so repeated API calls can be served faster without hitting the database.

This is particularly useful for:
- Campaign detail retrieval
- Campaign search results

---

### Redis Setup

Redis is run locally using Docker.

Start Redis container:

```bash
docker run -d -p 6379:6379 redis
````

Spring Boot connects to Redis using the following configuration:

```
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

---

### Spring Boot Configuration

Caching is enabled in the main application class.

```java
@EnableCaching
```

This allows Spring to manage caching using annotations.

---

### Redis Cache Configuration

A Redis cache manager configuration was added to explicitly define the cache manager bean.

```
src/main/java/com/crowdfund/backend/config/RedisConfig.java
```

```java
@Configuration
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }
}
```

---

### Cache Annotations Used

Caching is implemented at the **service layer**.

#### Cache Campaign by ID

```
CampaignServiceImpl#getApprovedCampaignById
```

```java
@Cacheable(value = "campaign", key = "#campaignId")
```

This caches campaign details to avoid repeated database queries.

---

#### Cache Campaign Search

```
CampaignServiceImpl#searchCampaigns
```

```java
@Cacheable(
 value = "campaignSearch",
 key = "#keyword + '-' + #category + '-' + #page + '-' + #size + '-' + #sortDir"
)
```

This caches campaign search results.

---

#### Cache Eviction on Update

```
CampaignServiceImpl#updateCampaign
```

```java
@CacheEvict(value = {"campaign","campaignSearch"}, allEntries = true)
```

Cache entries are cleared when campaigns are updated to prevent stale data.

---

### Dependency Added

```
spring-boot-starter-data-redis
```

This enables Redis integration with Spring Boot caching.

---

### Notes

* Redis is used only for read-heavy operations.
* Financial operations such as donations are **not cached**.
* Authentication flows using JWT are **not cached** for security reasons.



docker run -d -p 6379:6379 redis

```

### Notes
- Caching applied only to read-heavy endpoints.
- Donation and authentication flows are excluded from caching.
```



---

Below is a **complete Markdown section** you can paste directly into your **README.md**.
It includes **what Swagger is, why used, how implemented, changes made, dependencies fixed, and how to access it**.

---

````markdown
# 📘 API Documentation — Swagger (OpenAPI)

## Overview

Swagger (OpenAPI) is used in this project to provide **interactive API documentation** for the Crowdfunding backend.

It automatically scans all Spring Boot REST controllers and generates a **visual interface** where developers can:

- View all available APIs
- Inspect request and response models
- Test endpoints directly from the browser
- Understand the backend architecture easily

This helps developers and reviewers quickly explore the backend system without needing tools like Postman.

---

# Why Swagger Was Added

Swagger was integrated to improve:

- API discoverability
- Developer documentation
- Backend testing
- System demonstration during interviews
- Faster debugging of endpoints

Instead of manually writing API documentation, Swagger **generates it automatically from the codebase**.

---

# Swagger Dependency

Swagger was implemented using **SpringDoc OpenAPI**.

Dependency used:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
````

This dependency automatically provides:

* OpenAPI specification generator
* Swagger UI interface
* API schema generation

---

# Spring Boot Compatibility Adjustment

Originally, the project used:

```
Spring Boot 4.0.2
```

However, Swagger (SpringDoc OpenAPI) currently has compatibility issues with Spring Boot 4, which caused runtime errors during API documentation generation.

To ensure stable integration, the project was adjusted to:

```
Spring Boot 3.3.2
```

This version is fully compatible with:

* Java 17
* Swagger (SpringDoc)
* Spring Security
* Redis
* PostgreSQL
* Actuator
* JPA

No application logic or architecture was changed during this adjustment.

---

# Dependency Corrections

While integrating Swagger, several incorrect Maven dependencies were discovered and replaced.

### Removed Invalid Dependencies

These dependencies do not exist in Maven Central:

```xml
spring-boot-starter-webmvc
spring-boot-starter-webmvc-test
spring-boot-starter-data-jpa-test
```

### Correct Dependencies Used

These were replaced with the proper Spring Boot starters:

```xml
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-test
```

These provide the official Spring Boot web, persistence, and testing support.

---

# Swagger Configuration

A configuration class was added to define API metadata.

File location:

```
src/main/java/com/crowdfund/backend/config/SwaggerConfig.java
```

Implementation:

```java
package com.crowdfund.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Crowdfunding Platform API")
                        .description("API documentation for Crowdfunding Backend")
                        .version("v1.0"));
    }

}
```

This configuration defines:

* API title
* API description
* API version

---

# Security Configuration Update

Since the application uses **JWT-based authentication**, Swagger endpoints needed to be publicly accessible.

The following paths were added to the `permitAll()` section in `SecurityConfig`:

```java
"/swagger-ui/**",
"/swagger-ui.html",
"/v3/api-docs/**"
```

Example configuration snippet:

```java
.authorizeHttpRequests(auth -> auth
        .requestMatchers(
                "/api/v1/auth/**",
                "/api/v1/users",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**"
        ).permitAll()
        .anyRequest().authenticated()
)
```

This ensures:

* Swagger UI is publicly accessible
* All other APIs remain protected by JWT authentication.

---

# Swagger UI Access

After starting the application, Swagger documentation can be accessed at:

```
http://localhost:8080/swagger-ui/index.html
```

This interface allows users to:

* Browse all APIs
* View request parameters
* Execute API calls
* Inspect responses

---

# Swagger API Specification Endpoint

Swagger automatically exposes the OpenAPI specification at:

```
/v3/api-docs
```

This endpoint returns the complete **OpenAPI JSON specification** of the backend.

---

# Controllers Automatically Documented

Swagger automatically scans and documents all REST controllers including:

* AuthController
* CampaignController
* DonationController
* PaymentController
* UserController

No additional code is required for basic documentation.

---

# Optional Enhancements (Future)

Swagger supports additional annotations for richer documentation:

```
@Tag
@Operation
@Schema
@ApiResponse
```

Example:

```java
@Tag(name = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
```

These annotations improve:

* endpoint descriptions
* request schema documentation
* response details

---

# Summary

Swagger integration provides interactive API documentation for the Crowdfunding backend.

Key implementation steps:

1. Added SpringDoc OpenAPI dependency
2. Adjusted Spring Boot version for compatibility
3. Created Swagger configuration class
4. Updated Spring Security to allow Swagger endpoints
5. Corrected invalid Maven dependencies

Swagger now allows developers to **explore and test backend APIs directly from the browser**.

---

## 📘 Interview-style answer (short and clear):

In this project, I implemented **Swagger using SpringDoc OpenAPI** to automatically generate interactive API documentation for all REST endpoints. I added the `springdoc-openapi-starter-webmvc-ui` dependency, created a **Swagger configuration class** to define API metadata, and updated the **Spring Security configuration** to allow Swagger endpoints like `/swagger-ui` and `/v3/api-docs`. After running the application, Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`, where all APIs can be viewed and tested.

Swagger automatically scans the controllers and generates documentation, so **annotations are not mandatory**. However, for more detailed documentation we can add annotations like **`@Tag` on controllers and `@Operation` on endpoints** to provide descriptions of APIs and request/response models. In this project, the basic Swagger setup is implemented without annotations, but it can be enhanced with those annotations if more detailed API documentation is required.



---
# 🔜 Roadmap

Upcoming improvements:

- Docker deployment
- Campaign media uploads
- Admin dashboard APIs
- Payment failure recovery
- Monitoring & logging
---

