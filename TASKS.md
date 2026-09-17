# Live Coding — Interviewer Script

> Interviewer-facing. Do not share this file with the candidate.
> Answer key and scoring rubric are in `SOLUTION.md`.

**Format:** ~45–60 min live pairing. Candidate shares screen, drives in IntelliJ.
**Warm-up (5 min):** have them clone, run `./gradlew test`, and skim the code.
Ask them to briefly describe the architecture back to you (layering, where logic lives).

Move through the tasks in order. Each task escalates. It's fine to stop early —
the goal is signal on how a senior thinks, not completing every task.

---

## Task 1 — Add an endpoint (warm-up, ~8 min)

> "Add an endpoint to update a task's status, e.g. `PATCH /api/tasks/{id}/status`
> with a body like `{ "status": "IN_PROGRESS" }`."

**What to look for**
- Adds a DTO and validates the incoming status.
- Wires controller → service → repository cleanly (doesn't dump logic in the controller).
- Returns the updated task; 404 when the id doesn't exist (reuses `TaskNotFoundException`).

**Probes**
- What HTTP status for a successful update? Why PATCH vs PUT?
- What happens with an invalid/unknown status value?

---

## Task 2 — Validation & error handling (~8 min)

> "A blank title should be rejected with a 400. Confirm it is, and extend
> validation: titles must be unique (case-insensitive). A duplicate should
> return 409 Conflict."

**What to look for**
- Finds the existing `@Valid` + `GlobalExceptionHandler` wiring.
- Introduces a new exception + handler returning 409 (not a raw 500).
- Considers case-insensitive comparison and where the check belongs (service).

**Probes**
- Where should the uniqueness check live, and why not the controller?
- Race condition: two concurrent creates with the same title — how would you handle it?

---

## Task 3 — Find and fix a bug (~10 min)

> "There's a method `TaskService.getTasksByStatus(TaskStatus)`. A colleague says
> filtering by status returns the wrong tasks. Reproduce it with a test, then fix it."

**What to look for**
- Writes a failing test first (red), then fixes (green) — TDD instinct.
- Spots that the filter uses `!=` instead of `==` / `.equals`.
- Comments on enum comparison (`==` is fine for enums; `.equals` also fine).

See `SOLUTION.md` for the exact defect and fix.

---

## Task 4 — Write a test (~8 min)

> "Add a web-layer test proving that creating a task with a title longer than
> 120 characters returns 400."

**What to look for**
- Uses `MockMvc` (pattern already in `TaskControllerTest`).
- Understands the difference between unit tests (`TaskServiceTest`) and
  slice/integration tests.
- Asserts on status code and ideally the error body.

---

## Task 5 — Design discussion / stretch (remaining time)

Pick based on the candidate's strengths. Discussion, not necessarily code:

- **Persistence:** swap the in-memory store for JPA/Postgres. What changes? Migrations?
- **Pagination:** `GET /api/tasks` returns everything — design pagination + filtering.
- **Concurrency:** the repository is a `ConcurrentHashMap`. Where are the remaining
  race conditions (e.g. check-then-act in uniqueness)? How to make it safe?
- **Caching:** where would you add caching and how would you invalidate it?
- **Observability:** how would you add metrics/tracing/structured logging?

**What to look for**
- Reasons about trade-offs, not just names of technologies.
- Talks about failure modes, idempotency, and testing strategy.
