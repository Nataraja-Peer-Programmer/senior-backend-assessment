# Senior Backend Assessment

A small Spring Boot service used for a **live coding interview** for senior backend engineers.

It is a minimal in-memory **Task API**. There is no database and no external
setup — clone, open in IntelliJ, and run.

## Tech stack

- Java 17
- Spring Boot 3.3 (Web, Validation)
- Gradle (via the Gradle Wrapper — no local Gradle install needed)
- JUnit 5 + Spring Boot Test + MockMvc

## Getting started

Build and run the tests:

```bash
./gradlew test
```

Run the application:

```bash
./gradlew bootRun
```

The API starts on `http://localhost:8080`.

## API

| Method | Path              | Description                 |
|--------|-------------------|-----------------------------|
| GET    | `/api/tasks`      | List all tasks              |
| GET    | `/api/tasks/{id}` | Get a single task           |
| POST   | `/api/tasks`      | Create a task               |
| DELETE | `/api/tasks/{id}` | Delete a task               |

Create a task:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Write tests","description":"cover the service layer"}'
```

## Project layout

```
src/main/java/com/example/assessment
├── AssessmentApplication.java      # Spring Boot entry point
└── task
    ├── Task.java                   # domain model
    ├── TaskStatus.java             # TODO / IN_PROGRESS / DONE
    ├── TaskRepository.java         # in-memory store
    ├── TaskService.java            # business logic
    ├── TaskController.java         # REST endpoints
    ├── TaskNotFoundException.java
    ├── GlobalExceptionHandler.java
    └── dto/CreateTaskRequest.java  # validated request payload
```

The interviewer will guide you through a series of tasks during the session.
