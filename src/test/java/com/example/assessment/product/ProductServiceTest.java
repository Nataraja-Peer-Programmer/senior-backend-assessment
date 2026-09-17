package com.example.assessment.product;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit tests for {@link ProductService}.
 *
 * TODO (candidate): write tests for
 *   1. addProduct — a product is saved and gets an id.
 *   2. getProductsGroupedByCategory — products are grouped by their category.
 *
 * Tip: you can use the real in-memory repository (no Spring context needed):
 *     service = new ProductService(new ProductRepository());
 * which keeps these tests fast.
 */
class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(new ProductRepository());
    }

    // TODO: add your @Test methods here.
}
