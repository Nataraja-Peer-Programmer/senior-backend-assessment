package com.example.assessment.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Request payload for creating a product.
 *
 * The {@code id} is the client-supplied primary key: it is required, must be
 * positive, and must be unique. Creating a product with an id that already
 * exists must be rejected as a conflict.
 */
public class CreateProductRequest {

    /**
     * Client-supplied primary key. Required, positive, and unique — a second
     * create with the same id must be rejected as a conflict.
     */
    @NotNull(message = "id is required")
    @Positive(message = "id must be positive")
    private Long id;

    @NotBlank(message = "name must not be blank")
    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @NotBlank(message = "category must not be blank")
    @Size(max = 60, message = "category must be at most 60 characters")
    private String category;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "price must not be negative")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
