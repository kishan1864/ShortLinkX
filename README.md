# 🚀 ShortLinkX — AI-Powered URL Shortening & Analytics Platform

<div align="center">

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-Vite-61DAFB?logo=react&logoColor=black)](https://react.dev/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-336791?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![Groq](https://img.shields.io/badge/Groq-AI-black)](https://groq.com/)

**A secure, full-stack URL shortening platform with JWT authentication, PostgreSQL persistence, Docker deployment, and AI-powered analytics insights.**

### 🌐 Live Application

**Frontend:** https://shortlinkx-frontend-gewj.onrender.com

**Backend:** https://shortlinkx-backend-0nnu.onrender.com

### 📦 Source Code

**GitHub:** https://github.com/kishan1864/ShortLinkX

</div>

---

## 📋 Table of Contents

- [🎯 Project Overview](#-project-overview)
- [💡 Why ShortLinkX?](#-why-shortlinkx)
- [✨ Key Features](#-key-features)
- [🏗️ System Architecture](#️-system-architecture)
- [🔐 Authentication Architecture](#-authentication-architecture)
- [🔗 URL Shortening Architecture](#-url-shortening-architecture)
- [🧠 AI Analytics](#-ai-analytics)
- [📡 API Documentation](#-api-documentation)
- [🗃️ Database Design](#️-database-design)
- [🧰 Technology Stack](#-technology-stack)
- [📁 Project Structure](#-project-structure)
- [🚀 Quick Start](#-quick-start)
- [🔧 Configuration](#-configuration)
- [🐳 Docker](#-docker)
- [☁️ Deployment](#️-deployment)
- [🧪 Testing & Validation](#-testing--validation)
- [🐛 Troubleshooting](#-troubleshooting)
- [🔒 Security & Engineering Notes](#-security--engineering-notes)
- [📈 Scalability Roadmap](#-scalability-roadmap)
- [🗺️ Planned Enhancements](#️-planned-enhancements)
- [🎓 What This Project Demonstrates](#-what-this-project-demonstrates)
- [👨‍💻 Author](#-author)

---

# 🎯 Project Overview

**ShortLinkX** is a full-stack URL shortening platform designed to demonstrate practical backend engineering, secure authentication, database persistence, frontend-backend integration, AI API integration, and cloud deployment.

The project converts long URLs into short, shareable links and provides an authenticated dashboard experience around the shortened-link workflow.

The current implementation includes:

- Secure user registration and login
- JWT-based stateless authentication
- BCrypt password hashing
- Protected Spring Boot REST APIs
- PostgreSQL persistence with JPA/Hibernate
- 7-character Base62 short-code generation
- Short-code collision checking
- Public short-link redirection
- React + Vite frontend
- Groq-powered AI insights
- Dockerized Spring Boot backend
- Render cloud deployment
- Environment-based configuration and secrets

> **Current analytics scope:** the analytics screen currently uses seeded/demo metrics to demonstrate the AI integration. The AI analysis itself is generated dynamically from the supplied metrics. Real click-event collection and persistent analytics aggregation are planned enhancements.

---

# 💡 Why ShortLinkX?

Long URLs are often difficult to share, remember, display, and manage.

ShortLinkX provides a simple workflow:

```text
Long URL
   ↓
Validate
   ↓
Generate compact Base62 code
   ↓
Store in PostgreSQL
   ↓
Return short URL
   ↓
User shares short URL
   ↓
Short URL redirects to original URL
```

The project also demonstrates how an application can evolve from a basic CRUD-style backend into a platform with authentication, AI integration, containerization, and cloud deployment.

---

# ✨ Key Features

## 🔐 1. Authentication & Authorization

- User registration
- User login
- Stateless JWT authentication
- BCrypt password hashing
- Protected REST APIs
- Authenticated user profile endpoint
- Spring Security filter-chain integration
- CORS configuration for local and deployed frontend

---

## 🔗 2. URL Shortening

- Accepts HTTP/HTTPS URLs
- Validates URL input
- Generates a 7-character Base62 short code
- Checks for short-code collisions
- Stores URL mappings in PostgreSQL
- Returns a shareable short URL
- Supports public short-link redirection

Example:

```text
Original:
https://www.example.com/some/very/long/path

Generated:
http://localhost:8080/Ab12Xyz
```

Production redirect:

```text
https://shortlinkx-backend-0nnu.onrender.com/Ab12Xyz
```

---

## 🧠 3. AI-Powered Analytics Insights

ShortLinkX integrates a Groq-compatible AI API through the Spring Boot backend.

The backend sends analytics metrics such as:

```json
{
  "totalClicks": 12842,
  "uniqueVisitors": 8219,
  "mobilePercentage": 61,
  "desktopPercentage": 32,
  "tabletPercentage": 7,
  "topCountry": "India",
  "topBrowser": "Chrome",
  "growthPercentage": 18.2
}
```

The model produces:

- **Key observations**
- **Trends**
- **Recommendations**

The metrics can be changed through the demo analytics endpoint, so different input data can produce different AI-generated insights.

---

## 🗄️ 4. PostgreSQL Persistence

Current production database tables include:

```text
users
urls
```

The backend uses:

- Spring Data JPA
- Hibernate
- PostgreSQL
- Entity/repository separation
- Unique constraints
- Indexed short-code lookup

---

## 🐳 5. Dockerized Backend

The Spring Boot backend is containerized using a multi-stage Dockerfile:

```text
Maven + Java 21
      ↓
Build Spring Boot JAR
      ↓
Java 21 JRE image
      ↓
Run app.jar
```

---

## ☁️ 6. Cloud Deployment

The current application is deployed on Render:

```text
GitHub
   │
   ├── frontend/
   │       ↓
   │   Render Static Site
   │
   └── backend/
           ↓
       Docker Web Service
           │
           ├── Render PostgreSQL
           │
           └── Groq API
```

---

# 🏗️ System Architecture

## 🏗️ High-Level Architecture

```text
                         ┌──────────────────────┐
                         │        User          │
                         │      Browser         │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │   React + Vite       │
                         │     Frontend         │
                         └──────────┬───────────┘
                                    │
                              REST / JSON
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │    Spring Boot       │
                         │      Backend         │
                         └──────────┬───────────┘
                                    │
                 ┌──────────────────┼──────────────────┐
                 │                  │                  │
                 ▼                  ▼                  ▼
          Spring Security       URL Service       User Service
             + JWT                   │                  │
                                    │                  │
                 └──────────────────┼──────────────────┘
                                    │
                                    ▼
                              ┌─────────────┐
                              │ PostgreSQL  │
                              └─────────────┘
```


---

# 🔄 End-to-End Request Flow

## Normal protected API request

```text
Browser
   ↓
React
   ↓
Axios
   ↓
Authorization: Bearer <JWT>
   ↓
Spring Boot
   ↓
JwtAuthenticationFilter
   ↓
Token validation
   ↓
SecurityContext
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
PostgreSQL
```

---

# 🔐 Authentication Architecture

ShortLinkX uses **stateless JWT authentication**.

## Registration Flow

```text
Registration
    │
    ▼
React
    │
    ▼
POST /api/auth/register
    │
    ▼
AuthController
    │
    ▼
AuthService
    │
    ├── Validate request
    ├── Check email
    ├── BCrypt password hashing
    │
    ▼
UserRepository
    │
    ▼
PostgreSQL
```
### Password lifecycle

```text
User enters password
       ↓
BCrypt PasswordEncoder
       ↓
Password hash
       ↓
PostgreSQL
```

> BCrypt is a password hashing mechanism, not reversible encryption. The original password is not stored in the database.
## Login Flow
```text
Login
  │
  ▼
React
  │
  ▼
POST /api/auth/login
  │
  ▼
AuthenticationManager
  │
  ▼
DaoAuthenticationProvider
  │
  ▼
UserDetailsService
  │
  ▼
PostgreSQL
  │
  ▼
BCrypt password verification
  │
  ▼
JwtService
  │
  ▼
JWT access token
  │
  ▼
React
```

For protected requests, the frontend sends:

```http
Authorization: Bearer <JWT>
```

The custom JWT filter validates the token, loads the user details, and places the authenticated user into Spring Security's `SecurityContext`.

---




## Protected Request Flow

```text
React
   ↓
Authorization: Bearer <JWT>
   ↓
JwtAuthenticationFilter
   ↓
Extract JWT
   ↓
Validate signature + expiration
   ↓
Extract username/email
   ↓
Load UserDetails
   ↓
Create Authentication object
   ↓
SecurityContextHolder
   ↓
Protected Controller
```

---

# 🔗 URL Shortening Architecture

## Short-code generation

ShortLinkX uses the Base62 character set:

```text
0123456789
ABCDEFGHIJKLMNOPQRSTUVWXYZ
abcdefghijklmnopqrstuvwxyz
```

The generator creates a **7-character** code.

Example:

```text
A7xK29p
```

Before saving:

```text
Generate code
     ↓
Check existsByShortCode(...)
     ↓
Already exists?
   /      \
 Yes       No
  ↓         ↓
Generate   Save
again      URL
```

This provides collision protection at the application level.



## 🔗 URL Shortening Flow
```text
User
  │
  ▼
React Frontend
  │
  │ POST /api/urls
  ▼
UrlController
  │
  ▼
UrlService
  │
  ▼
ShortCodeGenerator
  │
  ├── Generate 7-character Base62-style code
  └── Check short-code uniqueness
  │
  ▼
UrlRepository
  │
  ▼
PostgreSQL
  │
  ▼
Short URL response
```
---



# 🔀 Redirect Flow

When a user opens:

```text
https://shortlinkx-backend-0nnu.onrender.com/Ab12Xyz
```

the backend performs:

```text
Short Code
    ↓
Find URL by shortCode
    ↓
Found?
 /     \
No      Yes
↓        ↓
404      Original URL
         ↓
       Redirect
```

The redirect endpoint is public because users need to open short links without authenticating.

---

# 🧠 AI Analytics

## AI Architecture
```text
UI 
│
▼
API
│
│ POST /api/urls
▼
DEMO
│
▼
AI
│
▼
MODEL
│
│
▼
OUT
│
UI
```


---

## Analytics Data Endpoint

```http
GET /api/analytics/demo
```

Returns the current demo metrics.

---

## Update Analytics Endpoint

```http
PUT /api/analytics/demo
Content-Type: application/json
Authorization: Bearer <JWT>
```

Example:

```json
{
  "totalClicks": 8500,
  "uniqueVisitors": 11200,
  "mobilePercentage": 22,
  "desktopPercentage": 78,
  "tabletPercentage": 0,
  "topCountry": "India",
  "topBrowser": "Brave",
  "growthPercentage": 27.5
}
```

The backend validates the metrics and stores the current demo state in memory.

---

## Generate AI Insights

```http
GET /api/analytics/ai-insights
Authorization: Bearer <JWT>
```

Example response:

```json
{
  "analytics": {
    "totalClicks": 12842,
    "uniqueVisitors": 8219,
    "mobilePercentage": 61.0,
    "desktopPercentage": 32.0,
    "tabletPercentage": 7.0,
    "topCountry": "India",
    "topBrowser": "Chrome",
    "growthPercentage": 18.2
  },
  "insights": "..."
}
```

The AI prompt instructs the model to analyze only the supplied metrics and return concise observations, trends, and recommendations.

---

# 📡 API Documentation

## Authentication APIs

### Register

```http
POST /api/auth/register
Content-Type: application/json
```

Example:

```json
{
  "name": "Kishan",
  "email": "kishan@example.com",
  "password": "StrongPassword123"
}
```

---

### Login

```http
POST /api/auth/login
Content-Type: application/json
```

Example:

```json
{
  "email": "kishan@example.com",
  "password": "StrongPassword123"
}
```

Returns a JWT access token.

---

## User API

### Current user

```http
GET /api/users/me
Authorization: Bearer <JWT>
```

Returns authenticated user profile information.

---

## URL API

### Create short URL

```http
POST /api/urls
Authorization: Bearer <JWT>
Content-Type: application/json
```

Request:

```json
{
  "originalUrl": "https://example.com"
}
```

Example response:

```json
{
  "originalUrl": "https://example.com",
  "shortUrl": "http://localhost:8080/Ab12Xyz",
  "shortCode": "Ab12Xyz"
}
```

---

### Redirect

```http
GET /{shortCode}
```

Public endpoint.

Example:

```http
GET /Ab12Xyz
```

---

## AI Analytics APIs

### Get demo analytics

```http
GET /api/analytics/demo
Authorization: Bearer <JWT>
```

### Update demo analytics

```http
PUT /api/analytics/demo
Authorization: Bearer <JWT>
Content-Type: application/json
```

### Get AI insights

```http
GET /api/analytics/ai-insights
Authorization: Bearer <JWT>
```

---

# 🗃️ Database Design

Current PostgreSQL schema:

```text
shortlinkx_db
│
└── public
    │
    ├── users
    │
    └── urls
```

---

## `users`

Current logical fields:

| Field | Purpose |
|---|---|
| `id` | Primary key |
| `name` | User display name |
| `email` | Unique login identifier |
| `password` | BCrypt password hash |
| `role` | User role |
| `created_at` | Account creation timestamp |

---

## `urls`

Current logical fields:

| Field | Purpose |
|---|---|
| `id` | Primary key |
| `original_url` | Original long URL |
| `short_code` | Unique generated code |
| `created_at` | Creation timestamp |

The `short_code` is uniquely constrained and used for redirect lookup.

---

# 🧰 Technology Stack

## Backend

| Technology | Role |
|---|---|
| Java 21 | Application language |
| Spring Boot 4.1.1 | Backend framework |
| Spring Web | REST API |
| Spring Security | Authentication & authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| Spring Data JPA | Persistence abstraction |
| Hibernate | ORM |
| Bean Validation | Request validation |
| Maven | Build & dependency management |
| PostgreSQL | Relational database |
| Docker | Containerization |

## Frontend

| Technology | Role |
|---|---|
| React | UI |
| Vite | Frontend build tooling |
| React Router | Routing |
| Axios | HTTP client |
| Lucide React | UI icons |
| Recharts | Charts |

## AI & Cloud

| Technology | Role |
|---|---|
| Groq API | AI-generated analytics insights |
| Render | Frontend/backend/database hosting |
| Docker | Backend deployment image |

---

# 📁 Project Structure

```text
ShortLinkX/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/shortlinkx/
│   │   │   │   ├── ai/
│   │   │   │   ├── auth/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── exception/
│   │   │   │   ├── repository/
│   │   │   │   ├── security/
│   │   │   │   ├── service/
│   │   │   │   ├── user/
│   │   │   │   └── util/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   ├── test/
│   │   ├── Dockerfile
│   │   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   ├── services/
│   │   └── styles/
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
├── README.md
└── ...
```

---

# 🚀 Quick Start

## Prerequisites

Install:

```text
Java 21
Maven
PostgreSQL
Node.js
npm
Git
```

Docker is required only for the containerized deployment workflow.

---

# 💻 Running the Backend Locally

Navigate to:

```bash
cd backend
```

Set environment variables.

### PowerShell example

```powershell
$env:DB_PASSWORD="your_local_postgres_password"
$env:JWT_SECRET="your_long_jwt_secret"
$env:GROQ_API_KEY="your_groq_api_key"
```

Run:

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

---

# ⚛️ Running the Frontend Locally

Navigate to:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start Vite:

```bash
npm run dev
```

Frontend:

```text
http://localhost:5173
```

The local frontend calls the local backend through:

```text
VITE_API_URL=http://localhost:8080
```

---

# 🔧 Configuration

## Backend `application.properties`

The application is configured through environment variables:

```properties
spring.application.name=shortlinkx

spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

server.port=${PORT:8080}

app.jwt.secret=${JWT_SECRET}
app.jwt.expiration=86400000

server.error.include-message=always

app.groq.api-key=${GROQ_API_KEY}
app.groq.model=${GROQ_MODEL:openai/gpt-oss-20b}
app.groq.endpoint=${GROQ_ENDPOINT:https://api.groq.com/openai/v1/chat/completions}
```

---

## 🔑 Environment Variables

### Backend

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD

JWT_SECRET

GROQ_API_KEY
GROQ_MODEL
GROQ_ENDPOINT
```

### Frontend

```text
VITE_API_URL
```

### Security rule

Never commit actual values for:

```text
DB_PASSWORD
JWT_SECRET
GROQ_API_KEY
```

Use deployment environment variables or local environment configuration.

---

# 🐳 Docker

The backend includes:

```text
backend/Dockerfile
```

The Dockerfile uses a multi-stage build:

```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build locally

From the `backend/` directory:

```bash
docker build -t shortlinkx-backend .
```

### Run locally

```bash
docker run -p 8080:8080 shortlinkx-backend
```

For a real deployment, database and secret configuration should be supplied through environment variables.

---

# ☁️ Deployment

ShortLinkX is currently deployed on Render.

## Production architecture

```text
GitHub Repository
        │
        ├───────────────┐
        │               │
        ▼               ▼
Render Static       Render Web
Site                Service
Frontend            Backend
                        │
                        ├── PostgreSQL
                        │
                        └── Groq API
```

### Frontend

```text
Service Type: Static Site
Root Directory: frontend
Build Command: npm ci && npm run build
Publish Directory: dist
```

Production API variable:

```text
VITE_API_URL=https://shortlinkx-backend-0nnu.onrender.com
```

### Backend

```text
Service Type: Web Service
Runtime: Docker
Root Directory: backend
Dockerfile: backend/Dockerfile
```

Database and AI credentials are provided through Render environment variables.

---

# 🔗 Production URLs

### Frontend

https://shortlinkx-frontend-gewj.onrender.com

### Backend

https://shortlinkx-backend-0nnu.onrender.com

### GitHub

https://github.com/kishan1864/ShortLinkX

---

# 🧪 Testing & Validation

## Authentication tests

```text
Register
  ↓
Login
  ↓
Receive JWT
  ↓
Call protected endpoint
```

Protected requests should contain:

```http
Authorization: Bearer <JWT>
```

---

## URL tests

Test:

```text
Valid HTTP URL
Valid HTTPS URL
Blank URL
Malformed URL
Duplicate short-code collision scenario
Non-existent short-code redirect
```

---

## AI analytics tests

1. Read current demo metrics.
2. Update the values.
3. Request AI insights.
4. Compare the result with the new inputs.

Example:

```text
Mobile: 61%
Desktop: 32%
Tablet: 7%
```

Change to:

```text
Mobile: 20%
Desktop: 75%
Tablet: 5%
```

Then request AI insights again to observe how the generated interpretation changes.

---

# 🧪 HTTP Status Codes Used in the Project

Typical API outcomes include:

```text
200 OK
    Successful request

201 Created
    Resource successfully created

400 Bad Request
    Invalid request or validation failure

401 Unauthorized
    Authentication is missing or invalid

403 Forbidden
    Access is not permitted

404 Not Found
    Requested resource does not exist

409 Conflict
    Resource conflicts with existing state

500 Internal Server Error
    Unexpected server-side failure
```

### Example

A protected endpoint opened directly in a browser without the JWT may return:

```text
403 Forbidden
```

because the browser request does not include the application's Bearer token.

---

# 🐛 Troubleshooting

## 1. React shows a blank page

Check:

```text
frontend/index.html
```

The Vite entry point should reference:

```text
/src/main.jsx
```

not the backend source tree.

---

## 2. CORS error

The backend CORS configuration must allow the actual frontend origin.

Current production frontend:

```text
https://shortlinkx-frontend-gewj.onrender.com
```

Local frontend:

```text
http://localhost:5173
```

---

## 3. Login succeeds but user is not stored correctly

Verify the frontend reads:

```text
accessToken
```

from the backend response rather than a different property name.

---

## 4. Render backend connects to localhost

A cloud deployment must never use:

```text
jdbc:postgresql://localhost:5432/...
```

for the production database.

Use:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
```

---

## 5. JWT secret startup failure

The application validates the JWT signing secret length.

Provide a sufficiently long secret through:

```text
JWT_SECRET
```

and never commit the real secret to GitHub.

---

## 6. AI endpoint returns `401` / `403`

Check:

```text
Authorization: Bearer <JWT>
```

The AI analytics endpoint is protected by Spring Security.

---

## 7. Production database looks empty

The Render PostgreSQL database is separate from the local PostgreSQL database.

```text
Local PostgreSQL
    ≠
Render PostgreSQL
```

A user registered locally does not automatically exist in the production database.

---

# 🔒 Security & Engineering Notes

## Password Security

Passwords are hashed using BCrypt before persistence:

```text
Plain password
      ↓
BCrypt
      ↓
Password hash
      ↓
PostgreSQL
```

---

## JWT Security

JWTs are used for stateless API authentication.

The backend validates:

- Token signature
- Token subject
- Token expiration

The JWT does not contain the user's password.

---

## Secret Management

Sensitive values are stored through environment variables:

```text
JWT_SECRET
GROQ_API_KEY
DB_PASSWORD
```

They are intentionally excluded from Git tracking through `.gitignore` rules.

---

## Input Validation

The URL creation request validates that the supplied URL is non-blank and follows an HTTP/HTTPS URL pattern before persistence.

---

## CORS

Only explicitly configured frontend origins are allowed.

Current origins include:

```text
http://localhost:5173
https://shortlinkx-frontend-gewj.onrender.com
```

---

# 📈 Scalability Roadmap

The current architecture is intentionally modular so that the application can grow without rewriting the core API contract.

## Current

```text
React
 ↓
Spring Boot
 ↓
PostgreSQL
 ↓
Groq
```

## Planned scalable architecture

```text
                    ┌───────────────┐
                    │ React Client  │
                    └───────┬───────┘
                            ↓
                    ┌───────────────┐
                    │ Load Balancer │
                    └───────┬───────┘
                            ↓
                ┌──────────────────────┐
                │ Spring Boot Instances│
                │  A     B     C       │
                └───────┬──────────────┘
                        │
             ┌──────────┴──────────┐
             ↓                     ↓
       Redis Cache          PostgreSQL
             │
             ↓
      Rate Limiting
             │
             ↓
      Async Analytics
             │
             ↓
          Groq AI
```

Planned system-design topics include:

- Redis caching
- Redis-based rate limiting
- Asynchronous analytics processing
- Message queues
- Load balancing
- Database indexing and optimization
- Horizontal scaling
- Observability and monitoring

---

# 🗺️ Planned Enhancements

The following features are **not claimed as fully implemented yet**:

### 🔗 URL Management

- URL history
- Custom short URLs
- URL ownership
- URL expiration

### 📊 Real Analytics

- Click-event tracking
- Unique visitor calculation
- Device detection
- Browser detection
- Location analytics
- Referrer analytics
- Historical analytics aggregation

### 📱 Utility Features

- QR code generation
- Shareable QR links

### ⚡ Performance

- Redis caching
- Redis rate limiting
- Request throttling

### 🧵 Distributed Processing

- Asynchronous analytics pipeline
- Message queues
- Background workers

### 📚 Developer Experience

- Swagger / OpenAPI
- More automated tests
- CI/CD pipeline
- Production monitoring
- Structured logging

---

# 🎓 What This Project Demonstrates

ShortLinkX is intended to demonstrate practical knowledge of:

```text
Java 21
   +
Spring Boot
   +
Spring Security
   +
JWT
   +
REST API Design
   +
JPA / Hibernate
   +
PostgreSQL
   +
React
   +
Axios
   +
Groq API Integration
   +
Docker
   +
Cloud Deployment
   +
Environment-based Configuration
   +
Real-world Debugging
```

The project also demonstrates the development lifecycle:

```text
Idea
 ↓
Architecture
 ↓
Implementation
 ↓
Integration
 ↓
Debugging
 ↓
Git / GitHub
 ↓
Docker
 ↓
Cloud Deployment
 ↓
Production Testing
```

---

# 🧩 Development & Debugging Highlights

Some of the most useful engineering problems solved during development were:

### Frontend entry-point issue

An incorrect Vite entry path caused a blank application.

**Resolution:** corrected the frontend entry-point reference.

### CORS preflight issue

The browser's `OPTIONS` request was initially blocked by the backend security configuration.

**Resolution:** configured CORS and explicitly allowed preflight requests.

### JWT response mismatch

The backend returned:

```json
{
  "accessToken": "..."
}
```

while the frontend was reading a different property.

**Resolution:** synchronized the frontend authentication context with the backend response contract.

### Production database connection

The first cloud deployment attempted:

```text
localhost:5432
```

which pointed to the container itself instead of Render PostgreSQL.

**Resolution:** moved production database configuration to environment variables.

### Groq integration

The AI service had to serialize metrics into a request payload, call the external AI API, parse the response, and return a clean application-level result.

---

# 🔄 Development Workflow with Git

Typical workflow used for ShortLinkX:

```bash
git status
git add .
git commit -m "your message"
git push origin main
```

Example:

```bash
git add backend/Dockerfile
git commit -m "chore: add Dockerfile for backend deployment"
git push origin main
```

The repository is maintained as a single monorepo containing both frontend and backend code.

---

# 📌 Project Status

| Area | Status |
|---|:---:|
| React frontend | ✅ |
| Spring Boot backend | ✅ |
| JWT authentication | ✅ |
| BCrypt password hashing | ✅ |
| PostgreSQL persistence | ✅ |
| URL shortening | ✅ |
| Short-code collision checking | ✅ |
| Public redirect | ✅ |
| Groq AI integration | ✅ |
| Demo analytics | ✅ |
| Docker backend | ✅ |
| Render deployment | ✅ |
| Real click analytics | 🔄 Planned |
| Redis caching | 🔄 Planned |
| Rate limiting | 🔄 Planned |
| QR generation | 🔄 Planned |
| Swagger/OpenAPI | 🔄 Planned |

---

# 👨‍💻 Author

<div align="center">

### Kishan Sahu

[![GitHub](https://img.shields.io/badge/GitHub-kishan1864-181717?logo=github)](https://github.com/kishan1864)

**Project:** [ShortLinkX](https://github.com/kishan1864/ShortLinkX)

**Live Demo:** [Open ShortLinkX](https://shortlinkx-frontend-gewj.onrender.com)

</div>

---

<div align="center">

## ⭐ ShortLinkX

**Shorter links. Smarter insights.**

Built as a practical full-stack engineering project with **Spring Boot, React, PostgreSQL, JWT, Docker, and AI integration.**

</div>
