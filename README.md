# Local Notice Hexagonal WebFlux

**Repository:** https://github.com/sisroberto801/local-notice-hex-webflux.git

## Hexagonal Architecture

The project follows a hexagonal (clean architecture) with the following layers:

```
src/main/java/com/hexagonal/notice/
├── NoticeApplication.java          # Main Spring Boot class
├── application/                   # Application layer
│   ├── controller/                # REST controllers
│   └── service/                   # Application services
├── domain/                        # Domain layer (core)
│   ├── model/                     # Domain entities
│   └── port/                      # Input/output ports
└── infrastructure/                # Infrastructure layer
    ├── config/                    # Configurations
    ├── entity/                    # Database entities
    ├── mapper/                    # Mappers between domain and infrastructure
    └── repository/                # Repository implementations
```

### Architecture Flow:
1. **Controller** receives HTTP requests
2. **Service** coordinates use cases
3. **Domain** contains pure business logic
4. **Infrastructure** implements technical details (DB, external APIs)

## Run Application

```bash
# Create database
createdb notice_db

# Run migrations
mvn flyway:clean flyway:migrate

# Get information
mvn flyway:info

# execute maven on application
mvn clean compile package

# Start application
mvn spring-boot:run
```

## API Documentation

Once the application is running, you can access the Swagger UI at:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

The Swagger documentation provides interactive API testing and detailed endpoint information.

### REST Endpoints

#### Users Management
- `GET /api/users` - Get all users
- `POST /api/users` - Create a new user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/email/{email}` - Get user by email (deprecated)

#### User Model
```json
{
  "id": 1,
  "username": "john_doe",
  "password": "hashed_password",
  "status": true,
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-15T10:30:00Z"
}
```

#### Profile Model
```json
{
  "id": 1,
  "userId": 1,
  "email": "john.doe@example.com",
  "fullName": "John Doe",
  "bio": "Software Developer",
  "avatarUrl": "https://example.com/avatar.jpg",
  "phoneNumber": "+1234567890",
  "address": "123 Main St, City, Country",
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-15T10:30:00Z"
}
```

## Database

- PostgreSQL: `notice_db`
- User: `postgres`
- Password: `postgres`
- Port: `5432`

## Migrations

Migrations are located in `src/main/resources/db/migration/`:
- `database_v1.sql` - Initial tables (users, profiles, tasks, task_assignments)

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.6/maven-plugin/build-image.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/4.0.6/reference/web/reactive.html)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/4.0.6/reference/data/sql.html#data.sql.r2dbc)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)

### Additional Links
These additional references should also help you:

* [R2DBC Homepage](https://r2dbc.io)

