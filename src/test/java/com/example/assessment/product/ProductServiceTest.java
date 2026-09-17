package com.example.assessment.product;

import com.example.assessment.product.dto.CreateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ProductService} using the real in-memory repository.
 */
class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(new ProductRepository());
    }

    @Test
    void addProduct_assignsIdAndPersistsFields() {
        Product created = service.addProduct(request("Laptop", "ELECTRONICS", "999.99"));

        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Laptop");
        assertThat(created.getCategory()).isEqualTo("ELECTRONICS");
        assertThat(created.getPrice()).isEqualByComparingTo("999.99");
    }

    @Test
    void getProductsGroupedByCategory_groupsByCategory() {
        service.addProduct(request("Laptop", "ELECTRONICS", "999.99"));
        service.addProduct(request("Phone", "ELECTRONICS", "499.00"));
        service.addProduct(request("Novel", "BOOKS", "12.50"));

        Map<String, List<Product>> grouped = service.getProductsGroupedByCategory();

        assertThat(grouped).containsOnlyKeys("ELECTRONICS", "BOOKS");
        assertThat(grouped.get("ELECTRONICS")).hasSize(2)
                .extracting(Product::getName)
                .containsExactlyInAnyOrder("Laptop", "Phone");
        assertThat(grouped.get("BOOKS")).hasSize(1)
                .extracting(Product::getName)
                .containsExactly("Novel");
    }

    @Test
    void getProductsGroupedByCategory_whenEmpty_returnsEmptyMap() {
        assertThat(service.getProductsGroupedByCategory()).isEmpty();
    }

    private CreateProductRequest request(String name, String category, String price) {
        CreateProductRequest req = new CreateProductRequest();
        req.setName(name);
        req.setCategory(category);
        req.setPrice(new BigDecimal(price));
        return req;
    }
}
