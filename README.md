# Cost-Share-Service
Cost-Share-Service is a Spring Boot-based microservice designed to facilitate expense sharing among groups of users. It leverages PostgreSQL as the primary database, Redis for caching, and Docker for containerization. The service includes key functionalities like user management, group management, expense tracking, and split calculations.

## Features
- User Management: Register and authenticate users with secure JWT-based authentication.
- Group Management: Create and manage groups for expense sharing.
- Expense Management: Add expenses, split costs among group members, and track contributions.
- Redis Caching: Speeds up frequent read operations for expense and group data.
- Pagination: Handles large datasets with efficient pagination for expense retrieval.
- API Architecture: RESTful APIs for seamless integration.

## Tech Stack
- Backend: Spring Boot (Java 17)
- Database: PostgreSQL
- Caching: Redis
- Containerization: Docker
- Build Tool: Maven

## Prerequisites
Ensure the following tools are installed on your system:

- Docker and Docker Compose
- Java 17
- Maven
- Postman or any API testing tool (optional)

## Setup and Run
1. Clone the Repository

```bash
git clone https://github.com/trongtinh7727/cost-share-service.git
cd cost-share-service
```

2. Build the Application

```bash
mvn clean install -DskipTests
```

3. Run with Docker

```bash
docker-compose up --build
```

4. Access the Application
- Backend API: http://localhost:8080 (or the port specified in .env)