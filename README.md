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
- Maven

---

## Features

- REST API for creating user activity events
- Request validation with Jakarta Validation
- PostgreSQL activity persistence
- Kafka producer for publishing activity events
- Kafka consumer for processing activity events
- Redis ZSET leaderboard storage
- Activity history API
- Leaderboard ranking API
- Swagger/OpenAPI documentation
- Docker Compose setup for Kafka, Redis, and PostgreSQL

---

## Architecture Flow

```text
POST /api/activities
        ↓
ActivityController
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
GET /api/activities
```

Response:

```json
[
  {
    "id": 1,
    "userId": 2,
    "type": "POST_CREATED",
    "points": 12,
    "createdAt": "2026-05-20T19:00:44.921912"
  }
]
```

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
PostgreSQL persistence
    ↓
Kafka event
    ↓
Kafka consumer
    ↓
Redis leaderboard
    ↓
REST API response
```

---

## Next Improvements

- Add global error handling
- Add pagination for activity history
- Add unit and integration tests
- Add user profiles and usernames
- Add JWT authentication
- Add Kafka retry and dead letter queue support