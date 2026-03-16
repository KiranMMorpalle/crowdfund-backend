# Changelog

All notable changes to this project will be documented here.

---

## [v1.0.0] - Day 1 - Foundation Setup

### Added
- User entity with UUID primary key
- Campaign entity with UUID primary key
- CampaignDocument entity
- Role enum for RBAC
- CampaignStatus enum
- Optimistic locking using @Version
- Index strategy for performance
- Repository layer implementation

### Design Decisions
- UUID chosen over Long for global uniqueness
- BigDecimal used for monetary safety
- Feature-based modular architecture

---

## [v1.1.0] — Day 2 (Service Layer Milestone)

### Added
- CampaignService interface
- CampaignServiceImpl with transactional boundaries
- Business validation rules
- Manual RBAC enforcement
- Custom exception hierarchy
- Optimistic locking using @Version
- Foreign key mapping (Campaign → User)

### Improved
- Domain integrity enforcement
- Clean separation of service logic
- Controlled state transitions (PENDING → APPROVED / REJECTED)

---

## [v1.2.0] — Day 3 (REST API Stabilization)

### Added
- CampaignController (Public APIs)
- AdminCampaignController (Admin APIs)
- ApiResponse<T> standardized response wrapper
- GlobalExceptionHandler for centralized exception handling
- ErrorResponse structured error model
- DTO boundary (CampaignResponseDTO, UserSummaryDTO)
- API versioning (`/api/v1`)


### Improved
- Removed entity exposure from controllers
- Proper HTTP status alignment (201, 400, 403, 404)
- Public campaign filtering (APPROVED only)
- Clean separation of public and admin endpoints
- Structured JSON error responses
- Repository method `findByIdAndStatus` for secure public access
- Campaign update API
- Campaign delete API
- Campaign listing enhanced with pagination, search by name, category filtering, and target amount sorting


### Verified
- Full Postman API testing completed
- RBAC enforcement validated
- Business validation scenarios tested
- Duplicate approval protection verified
- PUT /api/v1/campaigns/{id}
- DELETE /api/v1/campaigns/{id}
- GET /api/v1/campaigns?page={page}&size={size}
- GET /api/v1/campaigns?keyword={keyword}
- GET /api/v1/campaigns?category={category}
- GET /api/v1/campaigns?sortDir={asc|desc}
- GET /api/v1/campaigns?page={page}&size={size}

---

## [v1.3.0] - Day 4 (Donation Module & Concurrency)

### Added
- Donation entity
- DonationRepository
- DonationService
- DonationServiceImpl
- DonationController
- DonationStatus enum
- Campaign raisedAmount update on donation
- Donation listing endpoint

### Concurrency Protection
- Implemented optimistic locking using `@Version`
- Prevented lost update problem during concurrent donations
- HTTP `409 CONFLICT` returned when version mismatch occurs

### Improved
- Financial integrity protection
- Transactional donation updates
- Atomic campaign raisedAmount modification

### Verified
- Concurrent donation test scenarios validated
- Financial accuracy confirmed
- API tested via Postman

---

## [v2.0.0] - Day 5 (JWT Authentication & Security)

### Added
- JWT authentication system
- AuthController
- Login API
- Register API
- JWT token generation
- JWT validation filter
- Spring Security configuration
- CustomUserDetailsService implementation

### Security Improvements
- Stateless authentication using JWT
- Role-based access control
- Protected API endpoints
- Authentication required for campaign creation and donations

### Improved
- Removed temporary adminId query parameter
- Implemented proper authentication flow
- Secured campaign and donation APIs

### Verified
- Login authentication flow tested
- Token validation confirmed
- Protected APIs validated via Postman

---

## [v2.1.0] - Day 6 (Payment Module + Razorpay Integration)

### Added
- Payment entity
- PaymentStatus enum
- PaymentRepository
- PaymentService
- PaymentServiceImpl
- PaymentController
- PaymentVerificationRequest DTO
- Razorpay order creation API
- Payment verification API
- Razorpay webhook integration
- WebhookController for payment events

### Payment Gateway Integration
- Razorpay test environment integration
- Order creation using Razorpay API
- Secure webhook-based payment confirmation
- Signature verification using webhook secret

### Payment Flow Implemented

1. User creates donation
2. Backend creates Razorpay order
3. User completes payment through Razorpay
4. Razorpay sends webhook event
5. Backend verifies webhook signature
6. Payment marked SUCCESS
7. Donation marked SUCCESS

### Security Improvements
- Webhook signature verification
- Secure payment status updates
- Transactional consistency between Payment and Donation

### Improved
- Real-world payment gateway workflow
- Production-style webhook architecture
- Payment state tracking

### Verified
- Razorpay order creation tested
- Webhook event delivery verified via ngrok
- Payment verification API tested
- Donation status updated after successful payment
- Full payment lifecycle validated

---

---

## [v3.0.0] - Day 7 (Docker Containerization)

### Added
- Dockerfile for containerizing the Spring Boot backend
- Docker image build process
- Docker container runtime configuration
- Containerized deployment for the crowdfunding backend

### Deployment Setup
- Application packaged using Maven Wrapper (`./mvnw clean package`)
- Docker image built using `docker build`
- Container started using `docker run -p 8080:8080`

### Database Configuration
- Updated datasource URL to allow Docker container access:

  `jdbc:postgresql://host.docker.internal:5432/crowdfund`

- PostgreSQL `pg_hba.conf` updated to allow container connections.

### Verified
- Docker image successfully built
- Spring Boot application started inside container
- PostgreSQL connection established from container
- Application accessible via `http://localhost:8080`

---
## Current System Status

Completed modules:

- Campaign module
- Donation module
- Authentication module (JWT)
- Payment module (Razorpay + Webhooks)

System now supports a **complete crowdfunding lifecycle**:

User Registration  
→ Campaign Creation  
→ Campaign Approval  
→ Donation Creation  
→ Payment Processing  
→ Webhook Confirmation  
→ Donation Success

---

## [v3.1.0] - Redis Caching Integration

### Added
- Integrated Redis caching for performance optimization.
- Added Redis dependency: `spring-boot-starter-data-redis`.
- Enabled caching using `@EnableCaching` in the main application class.
- Implemented Redis cache manager configuration (`RedisConfig.java`).

### Caching Implemented
- Campaign details (`getApprovedCampaignById`)
- Campaign search results (`searchCampaigns`)

### Cache Eviction
- Cache invalidation added when campaigns are updated.

### Infrastructure
- Redis container support via Docker.

Run Redis locally:
 ``` docker run -d -p 6379:6379 redis ```


### Notes
- Caching applied only to read-heavy endpoints.
- Donation and authentication flows are excluded from caching.

---

## v3.2.0 — Swagger API Documentation

### Added
- Integrated **Swagger (SpringDoc OpenAPI)** for automatic API documentation.
- Added dependency:
    - `springdoc-openapi-starter-webmvc-ui`
- Created `SwaggerConfig` to define API metadata (title, description, version).

### Security Update
- Allowed Swagger endpoints in `SecurityConfig`:
    - `/swagger-ui/**`
    - `/swagger-ui.html`
    - `/v3/api-docs/**`
    - `http://localhost:8080/swagger-ui/index.html`
    - `http://localhost:8080/v3/api-docs`

### Dependency Fixes
- Removed invalid dependencies:
    - `spring-boot-starter-webmvc`
    - `spring-boot-starter-webmvc-test`
    - `spring-boot-starter-data-jpa-test`

- Replaced with correct dependencies:
    - `spring-boot-starter-web`
    - `spring-boot-starter-test`

### Compatibility Adjustment
- Changed **Spring Boot version** from `4.0.2` → `3.3.2` to ensure Swagger compatibility.

### Access
Swagger UI available at:



---

## v4.0.0 — JUnit Testing (Auth Module)

Implemented unit and controller testing for the authentication module.

Features:
- Added AuthServiceTest for service layer logic testing
- Added AuthControllerTest for REST API testing using MockMvc
- Implemented request validation for RegisterRequest
- Added validation exception handling in GlobalExceptionHandler
- Introduced TESTING.md documentation
- Introduced ARCHITECTURE.md describing production and testing architecture

---

## Next Planned Improvements

- Docker containerization
- Deployment configuration
- Monitoring & logging
- File uploads for campaign documents
- Admin moderation dashboard