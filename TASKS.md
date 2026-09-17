# Live Coding — Interviewer Script (Product API)

> Interviewer-facing outline. The condensed 30-minute plan is in
> `INTERVIEW_30MIN.md`; the reference solution + rubric in `SOLUTION.md`.
> Both of those are git-ignored and private.

**Exercise:** implement a small Product API.

1. **POST `/api/products`** — add a product (name, category, price).
2. **GET `/api/products`** — return all products **grouped by category**.
3. **Unit test** both.

**Format:** ~30 min live pairing. Candidate shares screen, drives in IntelliJ.
Hand them the stubbed version so they build it live (see `INTERVIEW_30MIN.md`
for how to prepare the skeleton).

---

## Warm-up (~3 min)

Have them run `./gradlew test` and skim the code.

> "Where would product endpoints go, and where does the business logic belong?"

Look for: controller → service → repository layering; notices the repository
already offers `findAll()` and `save()`.

---

## Task 1 — POST a product (~8 min)

> "Implement `POST /api/products`. A product has a name, category, and price."

**What to look for**
- A validated request DTO (`@Valid`, `@NotBlank`, non-negative price).
- Thin controller; create logic in the service.
- Returns 201 with the created product (generated id included).

**Probes**
- Blank name or negative price -> 400?
- Why a DTO instead of binding the entity directly?

---

## Task 2 — GET grouped by category (~11 min, core)

> "Implement `GET /api/products` to return products grouped by category, e.g.
> `{ "ELECTRONICS": [...], "BOOKS": [...] }`."

**What to look for**
- Idiomatic `Collectors.groupingBy(Product::getCategory)`.
- Returns `Map<String, List<Product>>`.
- Sensible on the empty case.

**Probes**
- Two products, same category?
- Null category — what happens with `groupingBy`? (NPE — guarded by `@NotBlank`.)
- Make grouping case-insensitive?

---

## Task 3 — Unit tests (~6 min)

> "Add unit tests for add-product and the grouping."

**What to look for**
- Plain JUnit service test with the real in-memory repo, and/or a `MockMvc` web test.
- Meaningful assertions (keys, per-category membership, empty case).
- Test isolation: resets the shared singleton repository between tests.

---

## Stretch / discussion (if time)

- `GET /api/products/{category}` for a single category.
- Case-insensitive categories.
- Swapping the in-memory store for a database — what changes, what to test.
- Concurrency: the repository is a `ConcurrentHashMap`; where are the remaining races?
