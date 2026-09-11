# ShortLinkX

> A secure and extensible URL-shortening platform built with React, Spring Boot, Spring Security, JWT, and PostgreSQL.

ShortLinkX is a full-stack URL-shortening project focused on practical backend engineering concepts such as REST API design, authentication, authorization, password security, database persistence, unique short-code generation, validation, exception handling, and frontend-backend integration.

The project is being developed incrementally: the core authentication and URL-shortening workflow is implemented first, while performance, analytics, and deployment features are planned as the next stages.

---

##  Current Features

### Authentication & Security
- User registration
- User login
- Spring Security integration
- JWT-based stateless authentication
- BCrypt password hashing
- Role-based security foundation
- Protected API endpoint for the authenticated user
- CORS configuration for the React frontend
- REST API development
- Request validation
- Global exception handling
- PostgreSQL persistence
- Spring Data JPA / Hibernate integration
- URL creation
- 7-character Base62-style random short-code generation
- Short-code collision checking
- Database-level uniqueness for short codes

### URL Shortening
- Create short URLs from long URLs
- 7-character Base62-style random short-code generation
- Short-code collision checking
- Database-level uniqueness for short codes
- PostgreSQL persistence
- REST API for URL creation

### Frontend
- React + Vite
- Login and registration screens
- Protected dashboard flow
- Axios API integration
- Authentication state using React Context
- Responsive UI

### Frontend UI / Prototype Scope
The following screens/components currently exist in the frontend as UI/prototype or presentation functionality:

- URL History UI
- Analytics UI
- QR Codes UI
- Profile UI

These frontend screens are **not yet backed by complete production backend functionality**.
---

##  High-Level Architecture


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

##  Authentication Flow

Registration Flow

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

Login Flow

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

Protected Request Flow

React
   |
   | Authorization: Bearer <JWT>
   v
JwtAuthenticationFilter
   |
   +-- Extract JWT
   |
   +-- Validate JWT
   |
   +-- Extract username
   |
   +-- Load UserDetails
   |
   +-- Validate token
   |
   v
SecurityContext
   |
   v
Protected Controller
   |
   v
Service
   |
   v
Repository
   |
   v
PostgreSQL

For protected requests, the frontend sends:

```http
Authorization: Bearer <JWT>
```

The custom JWT filter validates the token, loads the user details, and places the authenticated user into Spring Security's `SecurityContext`.

---

##  URL Shortening Flow

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

Example:

Original:
https://example.com/some/very/long/url

Short:
http://localhost:8080/X7kP92a
```

Collision Handling

Generate short code
        |
        v
Does code already exist?
       / \
     YES  NO
      |    |
      v    v
Generate  Save URL
another
code

---

##  Technology Stack

### Frontend
- React
- Vite
- JavaScript
- React Router
- Axios
- Recharts
- Lucide React

### Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Hibernate
- Bean Validation
- JJWT

### Database
- PostgreSQL

### Development
- IntelliJ IDEA
- Git
- GitHub
- Maven

---

##  Project Structure

```text
ShortLinkX/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/shortlinkx/
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
│   │   └── test/
│   └── pom.xml
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
├── .gitattributes
└── README.md
```

---

##  Running the Project Locally

### Prerequisites

Make sure you have:

- Java 21
- PostgreSQL
- Node.js and npm
- Git

### 1. Configure PostgreSQL

Create a PostgreSQL database named:

shortlinkx_db

The backend uses PostgreSQL on:

localhost:5432

### 2. Configure backend environment variables

Do not commit passwords or secrets to GitHub.

Configure these environment variables in your local IntelliJ run configuration:

```text
DB_PASSWORD=your_postgresql_password
JWT_SECRET=your_jwt_secret
```

The backend configuration should reference them instead of containing the real values directly.

### 3. Start the backend

From IntelliJ, run:

ShortlinkxApplication


or from the backend directory:

mvnw.cmd spring-boot:run


Backend:

http://localhost:8080


### 4. Start the frontend

Open another terminal:

cd frontend
npm install
npm run dev


Frontend:


http://localhost:5173


---

##  Current API Endpoints

### Authentication

POST /api/auth/register
POST /api/auth/login


### User

GET /api/users/me

Requires:
Authorization: Bearer <JWT>

### URL

POST /api/urls

Requires:

Authorization: Bearer <JWT>

---
## Core API Scope

Registration
Login
Authenticated current-user lookup
Authenticated URL creation

##  Core Data Model

### Users

users
├── id
├── name
├── email
├── password
├── role
└── created_at

### URLs

urls
├── id
├── original_url
├── short_code
└── created_at

Conceptually:

User 1 ─────────── N URLs

---

##  Security & Engineering Notes

- Passwords are stored as BCrypt hashes rather than plaintext.
- Authentication is stateless and uses JWT access tokens.
- Protected APIs require a valid Bearer token.
- Input validation is applied to API requests.
- Global exception handling is used for common API errors.
- Short-code uniqueness is checked at the application level and enforced at the database level.
- Database credentials and other secrets should be supplied through environment variables and must not be committed to the repository.

---

##  Planned Enhancements

The following features are planned for future iterations and are **not represented as completed functionality yet**:

### Performance & Scalability
- Redis caching for short-code lookups
- Redis-based rate limiting
- Horizontal scaling with multiple Spring Boot instances
- Load balancer
- Asynchronous analytics processing

### Analytics
- Click tracking
- Device analytics
- Browser analytics
- Location analytics
- Referrer analytics
- Analytics aggregation

### User Features
- Custom short URLs
- URL expiration
- URL history
- QR-code generation and download
- More complete profile management

### AI
- AI-generated analytics summaries
- Trend and anomaly insights
- Natural-language recommendations based on aggregated analytics

### Deployment
- Docker / Docker Compose
- Production configuration
- Monitoring and observability

---

##  Project Goal

The goal of ShortLinkX is to evolve from a working URL-shortening application into a production-oriented platform demonstrating:

```text
REST APIs
    +
Authentication & Authorization
    +
Database Design
    +
Caching
    +
Rate Limiting
    +
Analytics
    +
Asynchronous Processing
    +
AI-powered Insights
    +
Containerized Deployment
    +
Scalable System Design
```

---

##  Author

**Kishan Sahu**

GitHub:  
https://github.com/kishan1864

Project Repository:  
https://github.com/kishan1864/ShortLinkX

---

##  Current Project Status

**Core implementation complete:**

- Authentication
- JWT security
- PostgreSQL persistence
- REST APIs
- URL creation
- Short-code generation
- React frontend integration

**Advanced functionality:** under active development.
