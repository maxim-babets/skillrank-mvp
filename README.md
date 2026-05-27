# SkillRank MVP

SkillRank MVP is an event-driven leaderboard backend built with Spring Boot, Kafka, Redis, PostgreSQL, and Docker.

The project simulates user activity events such as creating posts or completing actions. Each activity is persisted in PostgreSQL, published to Kafka, consumed asynchronously, and used to update a Redis Sorted Set leaderboard.

---

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Kafka
- Spring Data Redis
- Spring Data JPA
- Hibernate
- PostgreSQL
- Apache Kafka
- Redis
- Swagger/OpenAPI
- Docker Compose
- JUnit 5
- Mockito
- Maven

---

## Features

- REST API for creating users
- REST API for creating user activity events
- Request validation with Jakarta Validation
- Global exception handling for validation errors
- Custom 404 handling for missing users
- PostgreSQL persistence
- Kafka producer for publishing activity events
- Kafka consumer for processing activity events
- Redis ZSET leaderboard storage
- Activity history API with pagination
- Leaderboard ranking API
- One-to-Many relationship between User and Activity
- Unit tests for service layer
- Swagger/OpenAPI documentation
- Docker Compose setup for Kafka, Redis, and PostgreSQL

---

## Architecture Flow

```text
POST /api/users
        ↓
Create User
        ↓
POST /api/activities
        ↓
ActivityController
        ↓
Request Validation
        ↓
ActivityService
        ↓
Save Activity to PostgreSQL
        ↓
Kafka Producer
        ↓
Kafka Topic: activity-events
        ↓
Kafka Consumer
        ↓
Redis ZSET: leaderboard
        ↓
GET /api/leaderboard/top
```

---

## API Endpoints

### Create User

```http
POST /api/users
```

Request body:

```json
{
  "username": "test_user",
  "email": "test@example.com",
  "password": "password123"
}
```

Response:

```json
{
  "id": 1,
  "username": "test_user",
  "email": "test@example.com",
  "createdAt": "2026-05-27T00:15:00"
}
```

---

### Create Activity

```http
POST /api/activities
```

Request body:

```json
{
  "userId": 1,
  "type": "POST_CREATED",
  "points": 5
}
```

Response:

```json
{
  "userId": 1,
  "type": "POST_CREATED",
  "points": 5,
  "message": "Activity processed successfully"
}
```

Validation error example:

```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2026-05-20T20:45:00"
}
```

User not found example:

```json
{
  "status": 404,
  "message": "User not found",
  "timestamp": "2026-05-27T00:15:00"
}
```

---

### Get Leaderboard

```http
GET /api/leaderboard/top
```

Response:

```json
[
  {
    "userId": 2,
    "rank": 1,
    "score": 12.0
  },
  {
    "userId": 1,
    "rank": 2,
    "score": 5.0
  }
]
```

---

### Get Activity History

```http
GET /api/activities?page=0&size=5
```

Response:

```json
{
  "content": [
    {
      "id": 1,
      "userId": 2,
      "type": "POST_CREATED",
      "points": 12,
      "createdAt": "2026-05-20T19:00:44.921912"
    }
  ],
  "totalElements": 2,
  "totalPages": 1,
  "size": 5,
  "number": 0
}
```

---

## Testing

The project currently includes unit tests for the service layer using:

- JUnit 5
- Mockito
- Mocked repository and Kafka producer interactions
- Mocked UserRepository behavior
- Service business logic verification
- User existence validation testing
- Activity/User relationship mapping tests
- Paginated activity history mapping

Example tested scenarios:

- Activity creation flow
- Kafka event publishing
- Repository save operations
- User existence validation
- Paginated activity history mapping

---

## How to Run

Start infrastructure:

```bash
docker compose up -d
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

Run tests:

```bash
./mvnw test
```

Open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Current Status

The current version supports a complete event-driven backend flow:

```text
HTTP request
    ↓
Request validation
    ↓
PostgreSQL persistence
    ↓
Kafka event
    ↓
Kafka consumer
    ↓
Redis leaderboard
    ↓
Paginated REST API response
```

---

## Next Improvements

- Add JWT authentication
- Add Kafka retry and dead letter queue support
- Add integration tests
- Create custom pagination response DTO
- Add CI/CD pipeline
