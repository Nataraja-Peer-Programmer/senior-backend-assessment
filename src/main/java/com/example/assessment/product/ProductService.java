package com.example.assessment.product;

import com.example.assessment.product.dto.CreateProductRequest;

import java.util.List;
import java.util.Map;

/**
 * Business logic for products.
 *
 * TODO (candidate): register this class as a Spring service bean so it can be
 * injected into the controller, then implement the two methods below.
 */
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * TODO: Add a new product to the repository.
     *
     * Build a {@link Product} from the incoming request and persist it via
     * {@link ProductRepository#save(Product)} (which assigns the id).
     * Return the saved product.
     */
    public Product addProduct(CreateProductRequest request) {
        throw new UnsupportedOperationException("TODO: implement addProduct");
    }

    /**
     * TODO: Return all products grouped by their category.
     *
     * Example result:
     *   { "ELECTRONICS": [laptop, phone], "BOOKS": [novel] }
     *
     * Hint: read all products from the repository and group them by category.
     */
    public Map<String, List<Product>> getProductsGroupedByCategory() {
        throw new UnsupportedOperationException("TODO: implement getProductsGroupedByCategory");
    }
}
