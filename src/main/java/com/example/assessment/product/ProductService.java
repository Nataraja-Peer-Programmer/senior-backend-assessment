package com.example.assessment.product;

import com.example.assessment.product.dto.CreateProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new product to the repository.
     */
    public Product addProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        return repository.save(product);
    }

    /**
     * Returns all products grouped by their category.
     *
     * Example: { "ELECTRONICS": [laptop, phone], "BOOKS": [novel] }
     */
    public Map<String, List<Product>> getProductsGroupedByCategory() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }
}
