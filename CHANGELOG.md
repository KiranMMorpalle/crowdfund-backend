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