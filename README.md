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
- Docker (Planned)
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

---

## 📂 Project Structure

com.crowdfund.backend
│
├── campaign
├── user
├── auth (planned)
├── ai (planned)
└── common

---

## 🧠 Design Decisions

- UUID used instead of Long for global uniqueness
- BigDecimal used for monetary calculations
- @Version used for optimistic locking
- Indexing applied based on query patterns
- Clean separation of domain logic

---

## 📊 Version Milestones

| Day   | Version | Description                         |
| ----- | ------- | ----------------------------------- |
| Day 1 | v1.0.0  | Domain + Repository Foundation      |
| Day 2 | v1.1.0  | Service Layer + Business Validation |
| Day 3 | v1.2.0  | REST Controllers                    |
| Day 4 | v1.3.0  | Donation + Concurrency              |
| Day 5 | v2.0.0  | JWT Security                        |
| Day 7 | v3.0.0  | Deployment                          |

---
## 📌 Current Status

Foundation complete.  
Ready for service layer implementation.

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

## 🔜 Roadmap

- Day 2 – Service Layer
- Day 3 – REST Controllers
- Day 4 – Donation + Concurrency
- Day 5 – JWT Security
- Day 6 – Testing
- Day 7 – Deployment
