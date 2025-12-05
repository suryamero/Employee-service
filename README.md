# Employee Service

A RESTful Spring Boot application for managing employees with a layered architecture and comprehensive exception handling.

## Features

- **Layered Architecture**: Follows best practices with Controller → Service → Repository layers
- **Exception Handling**: Centralized exception handling using `@RestControllerAdvice`
- **Data Validation**: Input validation using Jakarta Bean Validation
- **RESTful API**: Full CRUD operations for employee management
- **H2 Database**: In-memory database for development and testing
- **Unit Tests**: Comprehensive test coverage for business logic

## Technology Stack

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5 & Mockito

## Project Structure

```
src/
├── main/
│   ├── java/com/employee/service/
│   │   ├── controller/         # REST Controllers
│   │   ├── service/            # Business Logic Layer
│   │   ├── repository/         # Data Access Layer
│   │   ├── model/              # Entity Classes
│   │   ├── exception/          # Custom Exceptions & Global Handler
│   │   └── EmployeeServiceApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/employee/service/
        └── service/            # Unit Tests
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Build and Run

1. Clone the repository:
```bash
git clone https://github.com/suryamero/Employee-service.git
cd Employee-service
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Run Tests

```bash
mvn test
```

## API Endpoints

### Create Employee
```bash
POST /api/employees
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "IT",
  "salary": 75000.00
}
```

### Get All Employees
```bash
GET /api/employees
```

### Get Employee by ID
```bash
GET /api/employees/{id}
```

### Get Employee by Email
```bash
GET /api/employees/email/{email}
```

### Update Employee
```bash
PUT /api/employees/{id}
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.updated@example.com",
  "department": "Engineering",
  "salary": 85000.00
}
```

### Delete Employee
```bash
DELETE /api/employees/{id}
```

## Exception Handling

The application includes comprehensive exception handling:

- **ResourceNotFoundException** (404): When an employee is not found
- **DuplicateResourceException** (409): When attempting to create an employee with an existing email
- **MethodArgumentNotValidException** (400): When input validation fails
- **General Exception** (500): For unexpected server errors

### Error Response Format

```json
{
  "timestamp": "2025-12-05T17:47:03.310562575",
  "status": 404,
  "error": "Not Found",
  "message": "Employee not found with id: 999",
  "path": "/api/employees/999",
  "validationErrors": {}
}
```

## Validation Rules

- **firstName**: Required, cannot be blank
- **lastName**: Required, cannot be blank
- **email**: Required, must be a valid email format, must be unique
- **department**: Optional
- **salary**: Optional

## H2 Console

Access the H2 database console at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:employeedb`
- Username: `sa`
- Password: (leave empty)

## Example Usage

```bash
# Create an employee
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "department": "HR",
    "salary": 65000.00
  }'

# Get all employees
curl http://localhost:8080/api/employees

# Get employee by ID
curl http://localhost:8080/api/employees/1

# Update employee
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.updated@example.com",
    "department": "Engineering",
    "salary": 75000.00
  }'

# Delete employee
curl -X DELETE http://localhost:8080/api/employees/1
```

## License

This project is open source and available under the MIT License.