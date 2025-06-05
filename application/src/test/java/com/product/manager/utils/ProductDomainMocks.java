package com.product.manager.utils;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProductDomainMocks {

    public List<Product> createMockProducts() {
        return Arrays.asList(
                createProduct(1L, "V-NECH BASIC SHIRT", 100),
                createProduct(5L, "CONTRASTING LACE T-SHIRT", 650)
        );
    }

    public List<Product> createMockSortedProducts() {
        // Ordenados por sales desc
        return Arrays.asList(
                createProduct(5L, "CONTRASTING LACE T-SHIRT", 650),
                createProduct(1L, "V-NECH BASIC SHIRT", 100)
        );
    }

    public SortingCriteria createMockSortingCriteria() {
        return SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();
    }

    public Product createProduct(Long id, String name, Integer salesUnits) {
        return Product.builder()
                .id(id)
                .name(name)
                .salesUnits(salesUnits)
                .stockBySizes(Collections.emptyMap())
                .build();
    }

    public Product createProductWithStock(Long id, String name, Integer salesUnits,
                                           Integer stockS, Integer stockM, Integer stockL) {
        return Product.builder()
                .id(id)
                .name(name)
                .salesUnits(salesUnits)
                .stockBySizes(Map.of(
                        "S", stockS,
                        "M", stockM,
                        "L", stockL
                ))
                .build();
    }

    public List<Product> createTestProducts() {
        return Arrays.asList(
                // Datos del caso práctico
                createProduct(1L, "V-NECH BASIC SHIRT", 100,
                        Map.of("S", 4, "M", 9, "L", 0)), // ratio: 2/3 = 0.67

                createProduct(2L, "CONTRASTING FABRIC T-SHIRT", 50,
                        Map.of("S", 35, "M", 9, "L", 9)), // ratio: 3/3 = 1.0

                createProduct(5L, "CONTRASTING LACE T-SHIRT", 650,
                        Map.of("S", 0, "M", 1, "L", 0)) // ratio: 1/3 = 0.33
        );
    }

    public Product createProduct(Long id, String name, Integer salesUnits, Map<String, Integer> stock) {
        return Product.builder()
                .id(id)
                .name(name)
                .salesUnits(salesUnits)
                .stockBySizes(stock)
                .build();
    }
}
