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

### Verified

- Full Postman API testing completed
- RBAC enforcement validated
- Business validation scenarios tested
- Duplicate approval protection verified


## [v1.2.0] - Day 4 (Donation Module & Concurrency)

### Added
- Donation entity and repository
- Donation service and controller
- Transactional donation processing
- Optimistic locking for raisedAmount
- Conflict handling (HTTP 409)
- Donation listing endpoint

### Improved
- Financial integrity protection
- Concurrency-safe updates