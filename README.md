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

# Start application
mvn spring-boot:run
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

