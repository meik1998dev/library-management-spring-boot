# Library Management System

## API Documentation
For detailed information on API endpoints, please refer to our [Postman API Documentation](https://www.postman.com/research-geoscientist-22928730/workspace/lib-management/collection/13990907-637e6044-8876-45d9-8b9b-3f38f54817ca?action=share&creator=13990907).

## Description
A Spring Boot-based library management system designed to handle book and patron management, along with tracking borrowing records.

## Features
- CRUD operations for books and patrons.
- Borrowing and returning books.
- AOP-based logging and performance metrics.
- Transactional operations to ensure data integrity.
- Caching for improved performance.

## System Requirements
- Java JDK 11 or higher
- Maven
- PostgreSQL

## Installation

### Setting Up the Database
1. Install PostgreSQL.
2. Create a new database for the application.

### Configuring the Application
1. Clone the repository to your local machine.
2. Navigate to `src/main/resources` and update `application.properties` with your database details.

### Building the Application
In the project's root directory, run:
```bash
mvn clean install
```

### Usage
#### Running the Application
```bash
mvn spring-boot:run
```
### API Endpoints
Refer to the detailed API endpoints documentation in the [Postman API Documentation](https://www.postman.com/research-geoscientist-22928730/workspace/lib-management/collection/13990907-637e6044-8876-45d9-8b9b-3f38f54817ca?action=share&creator=13990907).


