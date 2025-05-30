# 📦 Subscription Service

## 📑 Описание

Приложение для управления пользователями и их цифровыми подписками. Реализовано на базе Spring Boot, использует PostgreSQL и Docker Compose.

---

## 🚀 Технологии

- Java 17  
- Spring Boot 3  
- Spring Data JPA
- Spring Security with OAuth2
- Hibernate
- PostgreSQL  
- Flyway  
- Docker, Docker Compose  
- Maven  
- SLF4J (Logback)
- Swagger
- JUnit
- Testcontainers
- Jacoco
- CI/CD

---

## 📡 Основные эндпоинты

### Пользователи:
- `POST /auth/register` - зарегистрировать в системе пользователя (создать пользователя может только SUPERVISOR)
- `POST /auth/login` - войти в систему пользователю
- `GET /users/{id}` — получить пользователя  
- `PUT /users/{id}` — обновить пользователя  
- `DELETE /users/{id}` — удалить пользователя  

### Подписки:
- `POST /users/{id}/subscriptions` — добавить подписку  
- `GET /users/{id}/subscriptions` — список подписок  
- `DELETE /users/{id}/subscriptions/{sub_id}` — удалить подписку  

### Дополнительно:
- `GET /subscriptions/top` — топ-3 самых популярных подписок  

---

## 🐳 Быстрый старт

```bash
docker-compose up --build -d
```

## Очистить контейнеры:
```bash
docker compose down
```

## Удалить образы:
```bash
docker rmi $(docker images --format "{{.ID}}") --force
```

## Запустить тесты:
```bash
mvn verify
```

## Приложение будет доступно по адресу:
```http://localhost:8888/api``` (со Swagger)

## Test coverage:
- 84%
