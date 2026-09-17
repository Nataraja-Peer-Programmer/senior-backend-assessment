package com.example.assessment.product;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple in-memory repository. No database required so the project runs
 * anywhere with zero setup during the interview.
 */
@Repository
public class ProductRepository {

    private final ConcurrentHashMap<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(sequence.incrementAndGet());
        }
        store.put(product.getId(), product);
        return product;
    }

    public void clear() {
        store.clear();
        sequence.set(0);
    }
}
