package com.example.assessment.product;

/**
 * Thrown when a product is created with an id that already exists.
 *
 * TODO (candidate): use this exception from the repository when a client-supplied
 * id collides with an existing product, and map it to HTTP 409 Conflict in
 * {@link GlobalExceptionHandler}. A short, informative message (including the id)
 * is enough. Extending {@link RuntimeException} keeps it unchecked so it can flow
 * up to the @RestControllerAdvice without cluttering method signatures.
 */
public class DuplicateProductException extends RuntimeException {

    public DuplicateProductException(Long id) {
        super("Product with id " + id + " already exists");
    }
}
