# ShoppingList API

A simple REST API for managing shopping lists and items, built with Spring Boot, PostgreSQL, JPA/Hibernate, and Flyway.

## Requirements (local)
- Java (your project uses Java 25)
- Maven
- Docker + Docker Compose

---

## Run with Docker (recommended)

This will start:
- PostgreSQL (container)
- The API (container) connected to PostgreSQL
- Flyway migrations run automatically on startup

### Start
```bash
docker compose up -d --build
