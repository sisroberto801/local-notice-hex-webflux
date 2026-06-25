# Local Notice Hexagonal WebFlux

**Repository:** https://github.com/sisroberto801/local-notice-hex-webflux.git

## Quick Start

### Option 1: Local Development (Terminal)
```bash
cp .env.localhost .env
mvn clean compile package
export $(grep -v '^#' .env | xargs)
mvn spring-boot:run
```

### Option 2: Docker Compose
```bash
cp .env.docker .env
docker-compose up postgres -d
docker-compose up
```

### Option 3: IntelliJ IDEA
```bash
cp .env.docker .env
docker-compose up postgres -d
# Then run the application from IntelliJ IDEA
```

## Project Structure

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

### Architecture Flow
1. **Controller** receives HTTP requests
2. **Service** coordinates use cases
3. **Domain** contains pure business logic
4. **Infrastructure** implements technical details (DB, external APIs)

## Environment Configuration

The project uses different environment files:
- `.env.localhost` - For local terminal execution
- `.env.docker` - For Docker Compose or IntelliJ IDEA
- `.env.example` - Template with all available variables

## Running the Application

### Local Development (Terminal)
```bash
# Copy environment configuration
cp .env.localhost .env

# Build the project
mvn clean compile package

# Export environment variables and run the application
export $(grep -v '^#' .env | xargs)
mvn spring-boot:run
```

### Docker Compose
```bash
# Copy Docker environment configuration
cp .env.docker .env

# Remove all images
docker-compose down --rmi all

# Start all services
ocker-compose up -d --build   
```

### IntelliJ IDEA
```bash
# Copy Docker environment configuration
cp .env.docker .env

# load all variables from .env into the Environment variables of Intell Idea
# Run the application from IntelliJ IDEA
```

## Docker Commands

```bash
# Build and start services
ocker-compose up -d --build   

# Start only the database
docker-compose up postgres

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Access the application
curl http://localhost:8000/api/users

# Access PostgreSQL directly
docker-compose exec postgres psql -U postgres -d notice_db

# View running containers
docker-compose ps

# Remove volumes (warning: this deletes all data)
docker-compose down -v

# Remove volumes and all images (complete cleanup)
docker-compose down --rmi all
```

## Services

- **PostgreSQL**: `localhost:5432`
- **Application**: `localhost:8000`
- **Swagger UI**: http://localhost:8000/swagger-ui.html

## API Documentation

Access the interactive API documentation:

- **Swagger UI**: http://localhost:8000/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8000/v3/api-docs

### REST Endpoints

#### Users Management
- `GET /api/users` - Get all users
- `POST /api/users` - Create a new user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/email/{email}` - Get user by email (deprecated)

#### Data Models

**User Model**
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

**Profile Model**
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

- **Database**: PostgreSQL `notice_db`
- **User**: `postgres`
- **Password**: `postgres`
- **Port**: `5432`

### Database Migrations

Migrations are located in `src/main/resources/db/migration/`:
- `database_v1.sql` - Initial tables (users, profiles, tasks, task_assignments)

### Migration Commands
```bash
# Run migrations
mvn flyway:clean flyway:migrate

# Get migration information
mvn flyway:info
```

## Technology Stack

- **Framework**: Spring Boot 4.0.6
- **Web**: Spring Reactive Web (WebFlux)
- **Database**: PostgreSQL with Spring Data R2DBC
- **Migrations**: Flyway
- **Architecture**: Hexagonal (Clean Architecture)
- **Build Tool**: Maven

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

