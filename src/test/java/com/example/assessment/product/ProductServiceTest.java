package com.example.assessment.product;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit tests for {@link ProductService}.
 *
 * TODO (candidate): write tests for
 *   1. addProduct — a product is saved under its client-supplied id.
 *   2. getProductsGroupedByCategory — products are grouped by their category.
 *   3. addProduct with a duplicate id — throws DuplicateProductException and
 *      does NOT overwrite the original.
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
