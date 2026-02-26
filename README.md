# Crowdfunding Backend System

A modular monolithic backend system built using Spring Boot,
designed with enterprise-grade architecture principles.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Docker (Planned)

---

## 🏗 Architecture Style

- Feature-based modular structure
- Layered architecture (Controller → Service → Repository)
- UUID-based primary keys
- Optimistic locking for concurrency
- Role-Based Access Control (RBAC)

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

### 🔐 Data Integrity

- NOT NULL constraints
- Foreign key constraints
- Unique email constraint
- Version column for concurrency safety

## 🔜 Roadmap

- Day 2 – Service Layer
- Day 3 – REST Controllers
- Day 4 – Donation + Concurrency
- Day 5 – JWT Security
- Day 6 – Testing
- Day 7 – Deployment
