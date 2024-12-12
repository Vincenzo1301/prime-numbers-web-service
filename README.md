# Prime Numbers Web Service

## Overview

This project is a RESTful web service designed to manage prime number data efficiently. The service enables clients to check if a number is prime and stores the results for future use. It is built to demonstrate foundational concepts in web services, concurrency, and RESTful API design.

## Features

- **Prime Number Validation**: 
  - The service checks whether a number is prime.
  - If the number is not registered, the client calculates its primality and submits the result to the server for storage.
- **Database Management**:
  - The service maintains a simple database of prime and non-prime numbers.
  - Flexible implementation: the database can be RAM-based, file-based, or other formats.
- **RESTful API**:
  - `GET`: Retrieve prime or non-prime status for a number.
  - `PUT`/`POST`: Submit new number records to the server.

## Advanced Features (Optional)

- **Cluster Setup**:
  - Support for multiple server instances operating in a synchronized cluster.
  - Two synchronization modes:
    - **On-demand**: Servers consult each other during queries.
    - **Periodic**: Servers synchronize their records at intervals.
  - Example cluster simulation with servers running on different ports.

---

## Technology Stack

- **Language**: Java, Python, or Node.js
- **REST Framework**: Jersey (Java) or equivalent alternatives.
- **Database**: In-memory storage or file-based persistence.

---

## Setup and Usage

### Prerequisites

- Java Development Kit (JDK)
- Maven (for Java-based implementation)
- Python (if using a Python-based framework)
- Docker (optional for deployment)

### Running the Service

1. Start the server:
   ```bash
   java -jar prime-service.jar
   ```
2. Interact with the API using curl or Postman:
   ```
   curl -X GET http://localhost:8080/prime/23
   ```
