# Task Management V2

## Description
Task Management V2 is a Spring Boot application designed to manage tasks efficiently. It utilizes a PostgreSQL database to store data and follows a layered architecture with controllers, services, repositories, and entities to perform CRUD operations.

## Features
- **Controllers**: Handle HTTP requests and responses.
- **Services**: Contain business logic.
- **Repositories**: Provide data access functionality.
- **Entities**: Represent database tables.

## Requirements
- Java 8 or higher
- Maven 3.6+
- PostgreSQL

## Getting Started

### Prerequisites
- Ensure you have PostgreSQL installed on your machine.
- Make sure you have the following settings in your `application.properties` file:

```properties
spring.application.name=jpa
spring.datasource.url=jdbc:postgresql://localhost:5432/TaskManagement
spring.datasource.username=postgres
spring.datasource.password=(your password)

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

