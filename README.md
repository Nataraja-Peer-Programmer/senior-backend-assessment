# Senior Backend Assessment

A small Spring Boot service used for a **live coding interview** for senior
backend engineers.

It is a minimal in-memory **Product API** — no database, no external setup.
The scaffolding is provided; during the session you implement the two endpoints,
their business logic, and unit tests.

## API reference

- **Swagger UI (opens in the browser, no setup):**
  **[View the API in Swagger UI](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/Nataraja-Peer-Programmer/senior-backend-assessment/main/openapi.yml)**
  — renders [`openapi.yml`](openapi.yml) via the hosted Swagger viewer. Just click
  it. (Requires this repo's `openapi.yml` to be pushed to `main` and internet
  access.)
- **Swagger UI (live, from the running app):** with the app running
  (`./gradlew bootRun`), open
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
  for interactive docs generated from the code. Spec at
  [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).
- **Offline (no app, no internet):** open [`api-docs.html`](api-docs.html) — the
  spec is embedded, so it renders from disk.
- **Raw contract:** [`openapi.yml`](openapi.yml) (OpenAPI 3.0).

## Tech stack

- Java 17
- Spring Boot 3.3 (Web, Validation)
- Gradle (via the Gradle Wrapper — no local Gradle install needed)
- JUnit 5 + Spring Boot Test + MockMvc

## Requirements

- **Git**
- **JDK 17 or newer** (verify with `java -version` — 17, 21, etc. all work)

Gradle itself does **not** need to be installed — the project ships the Gradle
Wrapper (`./gradlew`), which downloads the correct Gradle version automatically
on first use.

## Getting started

1. Clone the repo and open the folder in IntelliJ (**File → Open**, select this
   folder — don't open individual files). Let the Gradle import finish.
2. Verify the build from the project folder:

   ```bash
   ./gradlew test          # macOS / Linux
   gradlew.bat test        # Windows
   ```

   You should see `BUILD SUCCESSFUL`. This also downloads all dependencies so
   the IDE can resolve the Spring libraries.
3. If Spring imports show as unresolved in IntelliJ, click the **Reload** button
   in the Gradle tool window (the elephant icon on the right).
4. Run the app when you're ready:

   ```bash
   ./gradlew bootRun
   ```

   The API starts on `http://localhost:8080`.

---

## Your task

The endpoints and business logic are **not implemented yet**. Look for the
`TODO` markers in `ProductController` and `ProductService`.

1. **Wire up the REST controller** (`ProductController`) — the methods exist but
   have no request mappings yet. Add the Spring Web annotations so that:
   - `GET  /api/products` returns products grouped by category.
   - `POST /api/products` adds a product, returns 201, and validates the body.
2. **Wire up and implement `ProductService`:**
   - Register it as a Spring bean so it can be injected into the controller.
   - `addProduct(...)` — add a product to the repository.
   - `getProductsGroupedByCategory()` — return all products **grouped by
     category**.
3. **Treat the product `id` as a client-supplied primary key.** The `id` is
   **required** on the request and provided by the client — the repository does
   **not** generate it. It must be **unique**: a `POST` whose `id` already exists
   must be rejected as a conflict — return **409 Conflict** (do not overwrite the
   existing product, and do not 500). A request with no `id` is invalid → **400**.
   Enforce the uniqueness in the repository and map the resulting exception to
   409. Follow the `TODO` markers in `ProductRepository`,
   `DuplicateProductException`, and `GlobalExceptionHandler`.
4. **Write unit tests** for the service — including the duplicate-id case. Start
   in `ProductServiceTest`; there is an optional `ProductControllerTest` (MockMvc)
   stub for web-layer tests if you have time.

Already provided (you do **not** build these): the `Product` model, the
`ProductRepository` skeleton (`findAll` / `save` / `clear`), the
`CreateProductRequest` DTO with validation, `DuplicateProductException` (a ready
exception type to throw), and `GlobalExceptionHandler` (turns validation errors
into 400 — you extend it for 409).

Run `./gradlew test` as you go.

### Definition of done

- `POST /api/products` with a valid body (including a client-supplied `id`)
  returns **201** with the created product, echoing that `id`.
- `POST` with an `id` that already exists returns **409 Conflict**, and the
  existing product is left unchanged.
- `POST` with an invalid body (missing `id`, blank name, negative price) returns
  **400**.
- `GET /api/products` returns **200** with products grouped by category.
- Your unit tests pass (`./gradlew test` is green).

---

## API specification

The endpoints below are what you implement. A product has: `id` (the
client-supplied primary key), `name`, `category`, `price`.

| Method | Path            | Description                           |
|--------|-----------------|---------------------------------------|
| POST   | `/api/products` | Add a product                         |
| GET    | `/api/products` | List products **grouped by category** |

### POST `/api/products` — add a product

The client supplies the `id`; it is the primary key and must be unique.

```bash
curl -i -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"Laptop","category":"ELECTRONICS","price":999.99}'
```

Request body:

```json
{
  "id": 1,
  "name": "Laptop",
  "category": "ELECTRONICS",
  "price": 999.99
}
```

Response — **`201 Created`** (the product is echoed back with its `id`):

```json
{
  "id": 1,
  "name": "Laptop",
  "category": "ELECTRONICS",
  "price": 999.99
}
```

Duplicate `id` — **`409 Conflict`** (posting the same `id` twice; the first
product is kept, the second is rejected):

```json
{
  "status": 409,
  "detail": "Product with id 1 already exists"
}
```

Validation error — **`400 Bad Request`** (missing `id`, blank `name`, or negative
`price`):

```bash
curl -i -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"","category":"ELECTRONICS","price":999.99}'
```

```json
{
  "status": 400,
  "detail": "name: name must not be blank"
}
```

### GET `/api/products` — list products grouped by category

Request:

```bash
curl -i http://localhost:8080/api/products
```

Response — **`200 OK`** — a JSON object keyed by category, each value a list of
the products in that category:

```json
{
  "ELECTRONICS": [
    { "id": 1, "name": "Laptop", "category": "ELECTRONICS", "price": 999.99 },
    { "id": 2, "name": "Phone",  "category": "ELECTRONICS", "price": 499.00 }
  ],
  "BOOKS": [
    { "id": 3, "name": "Novel", "category": "BOOKS", "price": 12.50 }
  ]
}
```

When there are no products, the response is an empty object `{}`.

---

## Project layout

```
src/main/java/com/example/assessment
├── AssessmentApplication.java          # Spring Boot entry point
└── product
    ├── Product.java                    # domain model (id, name, category, price)
    ├── ProductRepository.java          # in-memory store   <-- ENFORCE UNIQUE id HERE (TODO)
    ├── ProductService.java             # business logic    <-- IMPLEMENT THE TODOs HERE
    ├── ProductController.java          # REST endpoints     <-- ADD THE ANNOTATIONS HERE
    ├── DuplicateProductException.java  # thrown on duplicate id (use it; map to 409)
    ├── GlobalExceptionHandler.java     # validation -> 400  <-- ADD 409 MAPPING HERE (TODO)
    └── dto/CreateProductRequest.java   # validated request payload (optional id)
```
