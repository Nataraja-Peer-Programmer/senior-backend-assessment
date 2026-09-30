# Live Coding — Interviewer Script (Product API)

> Interviewer-facing outline. The condensed 30-minute plan is in
> `INTERVIEW_30MIN.md`; the reference solution + rubric in `SOLUTION.md`.
> Both of those are git-ignored and private.
>
> API contract: `openapi.yml` — for a rendered view open `api-docs.html` in a
> browser (double-click; the spec is embedded, no server needed).

**Exercise:** implement a small Product API.

1. **POST `/api/products`** — add a product (client-supplied `id`, name,
   category, price); reject a duplicate `id` with **409 Conflict**.
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
offers `findAll()` and a `save()` skeleton, and that the product `id` is the
client-supplied primary key (the repository does not generate ids).

---

## Task 1 — POST a product (~7 min)

> "Implement `POST /api/products`. A product has a name, category, and price."

**What to look for**
- A validated request DTO (`@Valid`, `@NotNull` id, `@NotBlank`, non-negative price).
- Thin controller; create logic in the service.
- Returns 201 with the created product (echoing the client-supplied id).

**Probes**
- Blank name or negative price -> 400?
- Missing id -> 400?
- Why a DTO instead of binding the entity directly?

---

## Task 1b — id as a primary key + duplicate handling (~5 min)

> "The `id` is required on the request and chosen by the client — the repository
> does not generate it. Treat it as the primary key: it must be unique. What
> should happen if someone POSTs a product with an `id` that already exists?"

Expected answer: it's a **conflict → 409**, not a 400 (the request is well-formed)
and not a silent overwrite or 500. (A *missing* id, by contrast, is a 400.)

> "Implement it — enforce uniqueness and return 409."

**What to look for**
- Enforces uniqueness in the **repository** (single source of truth), not only
  in the controller.
- Does it **atomically** — `putIfAbsent` / `computeIfAbsent` on the
  `ConcurrentHashMap`, not a `containsKey`-then-`put` check (that's a race).
- Throws a dedicated exception (`DuplicateProductException`) and maps it to
  **409 Conflict** in `GlobalExceptionHandler`, reusing `ProblemDetail`.
- The original product is left unchanged when a duplicate is rejected.

**Probes**
- "Why 409 and not 400 or 422?" (well-formed request, conflicts with state)
- "Two requests with the same new id arrive at the same instant — can both win?"
  (only if the check isn't atomic)
- "How is a client-chosen id different from a server surrogate id? When would you
  actually let clients pick the key?" (natural keys, idempotent create, imports)
- "How would `PUT` (upsert) differ from this `POST`?"

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

> "Add unit tests for add-product, the grouping, and the duplicate-id case."

**What to look for**
- Plain JUnit service test with the real in-memory repo, and/or a `MockMvc` web test.
- Meaningful assertions (keys, per-category membership, empty case).
- A duplicate-id test: second insert throws / returns 409 **and** the first
  product is unchanged (assert membership, not just the exception).
- Test isolation: resets the shared singleton repository between tests.

---

## Stretch / discussion (if time)

- `GET /api/products/{category}` for a single category.
- Case-insensitive categories.
- Swapping the in-memory store for a database — what changes, what to test.
  (Note: with a real DB the duplicate-key rule becomes a unique-constraint
  violation to catch and map to 409 — same contract, different mechanism.)
- `PUT /api/products/{id}` as an upsert — how do POST and PUT differ here?
