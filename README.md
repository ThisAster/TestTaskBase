# 📦 Subscription Service

## 📑 Описание

Приложение для управления пользователями и их цифровыми подписками. Реализовано на базе Spring Boot, использует PostgreSQL и Docker Compose.

---

## 🚀 Технологии

- Java 17  
- Spring Boot 3  
- Spring Data JPA
- Spring Security
- Hibernate
- PostgreSQL  
- Flyway  
- Docker, Docker Compose  
- Maven  
- SLF4J (Logback)
- Swagger

---

## 📡 Основные эндпоинты

### Пользователи:
- `POST /users` — создать пользователя  
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
docker-compose up --build
```

## Приложение будет доступно по адресу:
```http://localhost:8888/api``` (со Swagger)
