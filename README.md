# Spring Boot N+1 Query Problem Demo

A hands-on Spring Boot project demonstrating the Hibernate N+1 query problem and its solutions using JPQL `JOIN FETCH`, Spring Data JPA `@EntityGraph`, and Hibernate Statistics.

## Overview

The N+1 query problem occurs when Hibernate executes one query to fetch parent entities and additional queries to fetch related entities individually. This can cause unnecessary database calls and affect application performance.

This project demonstrates how to reproduce the problem, identify the extra SQL queries, and optimize data fetching.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate ORM
- H2 In-Memory Database
- Maven
- Lombok
- JUnit 5

## Features

- Demonstrates the N+1 query problem using lazy loading.
- Fixes N+1 using JPQL `JOIN FETCH`.
- Fixes N+1 using Spring Data JPA `@EntityGraph`.
- Enables Hibernate SQL logging.
- Uses Hibernate Statistics to verify query counts through integration tests.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/departments/n-plus-one` | Demonstrates the N+1 query problem |
| GET | `/departments/join-fetch` | Fetches departments and employees using `JOIN FETCH` |
| GET | `/departments/entity-graph` | Fetches departments and employees using `@EntityGraph` |

## Query Comparison

For the sample data containing three departments:

| Approach | Expected SELECT Queries |
|---|---:|
| Lazy loading (N+1) | 4 |
| `JOIN FETCH` | 1 |
| `@EntityGraph` | 1 |

*Query counts are based on the sample data and current Hibernate configuration.*

## Getting Started

### Prerequisites

- JDK 21
- Maven
- IntelliJ IDEA or another Java IDE

### Run the Application

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/spring-boot-n-plus-one-demo.git
```

Navigate to the project directory:

```bash
cd spring-boot-n-plus-one-demo
```

Start the application:

```bash
mvn spring-boot:run
```

The application runs at `http://localhost:8080`.

Sample data is inserted automatically when the application starts.

## Testing

Run the integration tests:

```bash
mvn test -Dtest=NPlusOneQueryTest
```

The tests verify the expected query counts for the original implementation, `JOIN FETCH`, and `@EntityGraph`.

## Learning Outcomes

- Understand lazy loading in Hibernate.
- Identify the root cause of the N+1 query problem.
- Compare fetching strategies in Spring Data JPA.
- Inspect generated SQL statements.
- Measure query counts using Hibernate Statistics.
- Write integration tests to prevent performance regressions.

## License

This project is intended for learning, experimentation, and Java interview preparation.
