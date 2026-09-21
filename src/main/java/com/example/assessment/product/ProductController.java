package com.example.assessment.product;

import com.example.assessment.product.dto.CreateProductRequest;

import java.util.List;
import java.util.Map;

/**
 * REST controller for products.
 *
 * TODO (candidate): turn this into a working REST controller.
 *
 *   1. Register this class as a REST controller and map it to the base path
 *      "/api/products".
 *   2. Expose GET  /api/products  -> getProductsGroupedByCategory()
 *   3. Expose POST /api/products  -> addProduct(...), returning 201 Created,
 *      with request-body binding and validation on the incoming payload.
 *
 * The method bodies below are already correct — you only need to add the
 * appropriate Spring Web annotations so these endpoints are exposed.
 */
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * Returns all products grouped by category.
     * TODO: expose as GET /api/products
     */
    public Map<String, List<Product>> getProductsGroupedByCategory() {
        return service.getProductsGroupedByCategory();
    }

    /**
     * Adds a new product.
     * TODO: expose as POST /api/products (return 201, validate the request body)
     */
    public Product addProduct(CreateProductRequest request) {
        return service.addProduct(request);
    }
}
