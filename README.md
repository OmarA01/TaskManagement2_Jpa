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
spring.datasource.url=jdbc:postgresql://localhost:5432/TaskManagement
spring.datasource.username=postgres
spring.datasource.password=(your password)

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

## Paths

### User Endpoints
- `GET /api/users`
  - Retrieve all users in the database.
  
- `POST /api/users`
  - Add a new user with a JSON object in the request body.
  
- `GET /api/users/{id}`
  - Retrieve a user by ID.
  
- `DELETE /api/users/{id}`
  - Delete a user by ID.
  
- `PUT /api/users/{id}`
  - Update a user by ID with a JSON object in the request body.
  
- `GET /api/users/{id}/project`
  - Retrieve the project assigned to the user.
  
- `GET /api/users/{id}/project/tasks`
  - Retrieve tasks assigned to the user in the project.

### Task Endpoints
- `GET /api/tasks/{id}/users`
  - Retrieve users assigned to the task.
  
- `GET /api/tasks/{id}/projects`
  - Retrieve projects that include the task.
  
- `POST /api/tasks/{taskId}/users/{userId}`
  - Assign the task to a user.
  
- `DELETE /api/tasks/{taskId}/users/{userId}`
  - Remove the task from a user.
  
- `POST /api/tasks/{taskId}/projects/{projectId}`
  - Assign the task to a project.
  
- `DELETE /api/tasks/{taskId}/projects/{projectId}`
  - Remove the task from a project.

### Project Endpoints
- `GET /api/projects`
  - Retrieve all projects in the database.
  
- `POST /api/projects`
  - Add a new project with a JSON object in the request body.
  
- `GET /api/projects/{projectId}`
  - Retrieve a project by ID.
  
- `DELETE /api/projects/{projectId}`
  - Delete a project by ID.
  
- `PUT /api/projects/{projectId}`
  - Update a project by ID with a JSON object in the request body.
  
- `GET /api/projects/{projectId}/tasks`
  - Retrieve tasks assigned to the project.
  
- `GET /api/projects/{projectId}/users`
  - Retrieve users assigned to the project.
  
- `POST /api/projects/{projectId}/users/{userId}`
  - Assign a user to the project.
  
- `GET /api/projects/{projectId}/executeTasks`
  - Execute tasks assigned to the project.
