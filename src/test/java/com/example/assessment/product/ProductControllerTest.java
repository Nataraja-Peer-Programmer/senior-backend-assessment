package com.example.assessment.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web-layer tests exercising the full request/response cycle via MockMvc.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void resetRepository() {
        // The repository is a Spring singleton shared across test methods.
        // Reset it so each test starts from a clean, isolated state.
        repository.clear();
    }

    @Test
    void addProduct_returns201AndBody() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Laptop\",\"category\":\"ELECTRONICS\",\"price\":999.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("ELECTRONICS"));
    }

    @Test
    void addProduct_withBlankName_returns400() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"category\":\"ELECTRONICS\",\"price\":10}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProducts_returnsProductsGroupedByCategory() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Phone\",\"category\":\"ELECTRONICS\",\"price\":499.00}"));
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Novel\",\"category\":\"BOOKS\",\"price\":12.50}"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ELECTRONICS").isArray())
                .andExpect(jsonPath("$.ELECTRONICS[0].name").value("Phone"))
                .andExpect(jsonPath("$.BOOKS[0].name").value("Novel"));
    }
}
