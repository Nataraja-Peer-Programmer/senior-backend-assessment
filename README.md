# Senior Backend Assessment

A small Spring Boot service used for a **live coding interview** for senior backend engineers.

It is a minimal in-memory **Product API**. There is no database and no external
setup — clone, open in IntelliJ, and run.

## Tech stack

- Java 17
- Spring Boot 3.3 (Web, Validation)
- Gradle (via the Gradle Wrapper — no local Gradle install needed)
- JUnit 5 + Spring Boot Test + MockMvc

## Requirements

- **Git**
- **JDK 17** installed and available on your machine (verify with `java -version`).

  Gradle itself does not need to be installed — the project ships the Gradle
  Wrapper (`./gradlew`).

  > If you don't have JDK 17, Gradle will attempt to download one automatically
  > (via foojay.io) on the first build. This may not work on restricted/corporate
  > networks, so please install JDK 17 beforehand.

## Getting started

Build and run the tests:

```bash
./gradlew test
```

> First run downloads Gradle and, if needed, a JDK 17. This is a one-time
> download and is cached for later runs.

Run the application:

```bash
./gradlew bootRun
```

The API starts on `http://localhost:8080`.

## API

| Method | Path            | Description                          |
|--------|-----------------|--------------------------------------|
| GET    | `/api/products` | List products **grouped by category**|
| POST   | `/api/products` | Add a product                        |

Add a product:

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","category":"ELECTRONICS","price":999.99}'
```

Get products grouped by category:

```bash
curl http://localhost:8080/api/products
```

```json
{
  "ELECTRONICS": [
    { "id": 1, "name": "Laptop", "category": "ELECTRONICS", "price": 999.99 }
  ],
  "BOOKS": [
    { "id": 2, "name": "Novel", "category": "BOOKS", "price": 12.50 }
  ]
}
```

## Project layout

```
src/main/java/com/example/assessment
├── AssessmentApplication.java          # Spring Boot entry point
└── product
    ├── Product.java                    # domain model (id, name, category, price)
    ├── ProductRepository.java          # in-memory store
    ├── ProductService.java             # business logic (add, group by category)
    ├── ProductController.java          # REST endpoints
    ├── GlobalExceptionHandler.java     # validation -> 400
    └── dto/CreateProductRequest.java   # validated request payload
```

The interviewer will guide you through the exercise during the session.
