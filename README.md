# Local Notice Hexagonal WebFlux

## Arquitectura Hexagonal

El proyecto sigue una arquitectura hexagonal (clean architecture) con las siguientes capas:

```
src/main/java/com/hexagonal/notice/
├── NoticeApplication.java          # Clase principal de Spring Boot
├── application/                   # Capa de aplicación
│   ├── controller/                # Controladores REST
│   └── service/                   # Servicios de aplicación
├── domain/                        # Capa de dominio (core)
│   ├── model/                     # Entidades de dominio
│   └── port/                      # Puertos de entrada/salida
└── infrastructure/                # Capa de infraestructura
    ├── config/                    # Configuraciones
    ├── entity/                    # Entidades de base de datos
    ├── mapper/                    # Mappers entre dominio e infraestructura
    └── repository/                # Implementaciones de repositorios
```

### Flujo de la Arquitectura:
1. **Controller** recibe peticiones HTTP
2. **Service** coordina casos de uso
3. **Domain** contiene la lógica de negocio pura
4. **Infrastructure** implementa detalles técnicos (BD, APIs externas)



## Ejecutar Aplicación

```bash
# Crear base de datos
createdb notice_db

# Ejecutar migraciones
mvn flyway:clean flyway:migrate

# Informacion
mvn flyway:info

# Iniciar aplicación
mvn spring-boot:run
```

## Base de Datos

- PostgreSQL: `notice_db`
- Usuario: `postgres`
- Contraseña: `postgres`
- Puerto: `5432`

## Migraciones

Las migraciones están en `src/main/resources/db/migration/`:
- `database_v1.sql` - Tablas iniciales (users, profiles, tasks, task_assignments)

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

