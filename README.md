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
```

You can **directly paste this entire block into README.md**.




---
# 🔜 Roadmap

Upcoming improvements:

- Docker deployment
- Campaign media uploads
- Admin dashboard APIs
- Payment failure recovery
- Monitoring & logging
---

