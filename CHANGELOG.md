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

## Next Planned Improvements

- Docker containerization
- Deployment configuration
- Monitoring & logging
- File uploads for campaign documents
- Admin moderation dashboard