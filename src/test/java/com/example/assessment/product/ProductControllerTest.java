package com.example.assessment.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Web-layer tests for the Product endpoints (optional / stretch).
 *
 * TODO (candidate, if time): use MockMvc to test the HTTP layer, e.g.
 *   - POST /api/products returns 201 with the created product.
 *   - POST with an invalid body returns 400.
 *   - GET /api/products returns products grouped by category.
 *
 * Note: the repository is a Spring singleton shared across test methods, so you
 * may want to reset it between tests to keep them isolated.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    // TODO: add your @Test methods here.
}
