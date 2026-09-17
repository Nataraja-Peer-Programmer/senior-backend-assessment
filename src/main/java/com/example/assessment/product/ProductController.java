package com.example.assessment.product;

import com.example.assessment.product.dto.CreateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * Returns all products grouped by category.
     */
    @GetMapping
    public Map<String, List<Product>> getProductsGroupedByCategory() {
        return service.getProductsGroupedByCategory();
    }

    /**
     * Adds a new product.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@Valid @RequestBody CreateProductRequest request) {
        return service.addProduct(request);
    }
}
