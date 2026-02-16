# Changelog

All notable changes to this project will be documented here.

---

## [v1.0] - Day 1 - Foundation Setup

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
