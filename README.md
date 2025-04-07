# Student Marks Management System

A Spring Boot application with PostgreSQL for managing student marks.

## Features

- **Teacher Features**:

  - View marks of a student based on roll number and exam type
  - Update marks for a student by roll number, subject, and exam type

- **Top 3 Rankers**:
  - View top 3 students based on the selected exam type

## Tech Stack

- **Backend**:

  - Java 8
  - Spring Boot with XML-based configuration (no annotations)
  - PostgreSQL

- **Frontend**:
  - HTML
  - CSS

## Setup Instructions

### Prerequisites

- Java 8 JDK
- Maven
- PostgreSQL

### Database Setup

1. Create a PostgreSQL database named `student_marks`:

```sql
CREATE DATABASE student_marks;
```

2. Run the schema.sql script to create tables and insert default subjects:

```bash
psql -U postgres -d student_marks -f schema.sql
```

### Application Configuration

1. Modify `src/main/resources/application.properties` with your PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_marks
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Build and Run

1. Build the application:

```bash
mvn clean package
```

2. Run the application:

```bash
java -jar target/student-marks-management-0.0.1-SNAPSHOT.jar
```

3. Access the application:

```
http://localhost:8080
```

## Usage

1. **Add Students**:

   - Navigate to the "Students" section
   - Enter student name and roll number

2. **Manage Marks**:

   - Navigate to the "Marks Management" section
   - Search for a student by roll number and exam type
   - Add or update marks for subjects

3. **View Top Rankers**:
   - Navigate to the "Top Rankers" section
   - Select exam type to view the top 3 students
