package com.example.assessment.product;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory repository. No database required so the project runs
 * anywhere with zero setup during the interview.
 */
@Repository
public class ProductRepository {

    private final ConcurrentHashMap<Long, Product> store = new ConcurrentHashMap<>();

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * Persists a product using its {@code id} as the primary key.
     *
     * <p>TODO (candidate): the id is supplied by the caller — this repository does
     * NOT generate ids. Store the product under {@code product.getId()}, but treat
     * that id as a UNIQUE primary key:
     *   - Inserting a product whose id already exists must be rejected — throw
     *     {@link DuplicateProductException} rather than silently overwriting the
     *     existing product.
     *   - Enforce uniqueness ATOMICALLY (hint: {@link ConcurrentHashMap#putIfAbsent})
     *     so two concurrent inserts of the same id cannot both succeed — a plain
     *     "containsKey? then put" check is a race.
     */
    public Product save(Product product) {
        store.put(product.getId(), product);
        return product;
    }

    public void clear() {
        store.clear();
    }
}
