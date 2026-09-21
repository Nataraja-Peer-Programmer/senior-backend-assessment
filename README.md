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

## Your task

The scaffolding is in place, but the endpoints and business logic are **not
implemented yet**. Your job during the session:

1. **Wire up the REST controller** (`ProductController`). The methods exist but
   have no request mappings yet — add the Spring Web annotations so that:
   - `GET  /api/products`  returns products grouped by category.
   - `POST /api/products`  adds a product, returns 201, and validates the body.
2. **Wire up `ProductService`** as a Spring bean and implement its logic:
   - Register the class so it can be injected into the controller.
   - `addProduct(...)` — add a product to the repository.
   - `getProductsGroupedByCategory()` — return all products **grouped by
     category**, e.g. `{ "ELECTRONICS": [...], "BOOKS": [...] }`.
3. **Write unit tests** for the service. Start in `ProductServiceTest`; there is
   an optional `ProductControllerTest` (MockMvc) stub for web-layer tests if you
   have time.

The repository, request DTO, validation, and error handling are already provided.
Look for the `TODO` markers in `ProductController` and `ProductService`.

Run `./gradlew test` as you go.

## Project layout

```
src/main/java/com/example/assessment
├── AssessmentApplication.java          # Spring Boot entry point
└── product
    ├── Product.java                    # domain model (id, name, category, price)
    ├── ProductRepository.java          # in-memory store (findAll, save)
    ├── ProductService.java             # business logic  <-- IMPLEMENT THE TODOs HERE
    ├── ProductController.java          # REST endpoints (already wired)
    ├── GlobalExceptionHandler.java     # validation -> 400
    └── dto/CreateProductRequest.java   # validated request payload
```
