# SkillRank MVP

SkillRank MVP is an event-driven leaderboard backend built with Spring Boot, Kafka, Redis, and Docker.

The project simulates user activity events such as creating posts or completing actions. Each activity is published to Kafka, consumed asynchronously, and used to update a Redis Sorted Set leaderboard.

---

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Kafka
- Spring Data Redis
- Apache Kafka
- Redis
- Docker Compose
- Maven

---

## Features

- REST API for creating user activity events
- Request validation with Jakarta Validation
- Kafka producer for publishing activity events
- Kafka consumer for processing activity events
- Redis ZSET leaderboard storage
- Leaderboard ranking API
- Docker Compose setup for Kafka and Redis

---

## Architecture Flow

```text
POST /api/activities
        ↓
ActivityController
        ↓
ActivityService
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

## How to Run

Start Kafka and Redis:

```bash
docker compose up -d
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

---

## Current Status

The current version supports an end-to-end event-driven flow:

```text
HTTP request
    ↓
Kafka event
    ↓
Kafka consumer
    ↓
Redis leaderboard
    ↓
API response
```

---

## Next Improvements

- Add PostgreSQL for persistent activity history
- Add Swagger/OpenAPI documentation
- Add global error handling
- Add unit and integration tests
- Add user profiles and usernames
