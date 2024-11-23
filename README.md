# Assignment Content

## Question

Using the REST API, implement a small **prime numbers web service**.

### Requirements:

- The server maintains a **database of prime and non-prime numbers**.
    - The "database" can be implemented in any way you choose (e.g., a simple text file or a RAM-stored list).
- **Client Requests**:
    - Clients send requests with a number to check if it is registered as **prime** or **non-prime**.
    - If the number is not registered, the client:
        1. Calculates whether the number is prime.
        2. Sends a request to the server to store the result in the server's records.

### Implementation Notes:

- Use any algorithm or API for prime number checking, including the **naive algorithm** shown during the lecture on Java
  concurrency.
- You can use:
    - **Jersey library for Java**.
    - Any other REST API technology (e.g., Python, Node.js, etc.).
        - For Python, check the link provided in the *Practicalities* section.
        - For Java, a tutorial link is provided in the *Practicalities* section on Blackboard.
- **Correct REST API operations**:
    - Use `GET` for retrieving numbers.
    - Use `PUT` or `POST` for submitting numbers.

---

## Optional (Advanced) Task

### Cluster Setup:

- Assume multiple servers, with each server:
    - Aware of the others' addresses.
    - Able to consult the others before responding to queries.
    - Alternatively, periodically synchronizing their databases to maintain up-to-date records.

### Implementation Details:

- You can choose between:
    1. **On-demand synchronization**: Servers consult each other with each query.
    2. **Periodic synchronization**: Servers periodically share updates to keep records fresh.

- **Cluster Simulation**:
    - Run multiple instances of the server on different ports on the same machine.

  Example commands:
  ```bash
  java -Dserver.port=8080 -Dcluster.servers=http://localhost:8081,http://localhost:8082 -jar my-app.jar
  java -Dserver.port=8081 -Dcluster.servers=http://localhost:8080,http://localhost:8082 -jar my-app.jar
  java -Dserver.port=8082 -Dcluster.servers=http://localhost:8080,http://localhost:8081 -jar my-app.jar
  ```