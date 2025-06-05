package com.product.manager.domain;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCalculateStockRatioWithTwoDecimals() {
        // Given
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .salesUnits(100)
                .stockBySizes(Map.of(
                        "S", 10,
                        "M", 0,
                        "L", 0
                ))
                .build();

        // When
        Double ratio = product.getStockRatio();

        // Then
        assertEquals(0.33, ratio);
    }

    @Test
    void shouldCalculateStockRatioForTwoThirds() {
        // Given
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .salesUnits(100)
                .stockBySizes(Map.of(
                        "S", 10,
                        "M", 5,
                        "L", 0
                ))
                .build();

        // When
        Double ratio = product.getStockRatio();

        // Then
        assertEquals(0.67, ratio);
    }

    @Test
    void shouldCalculateStockRatioForPerfectNumbers() {
        // Given
        Product productFull = Product.builder()
                .stockBySizes(Map.of("S", 1, "M", 1, "L", 1))
                .build();

        Product productEmpty = Product.builder()
                .stockBySizes(Map.of("S", 0, "M", 0, "L", 0))
                .build();

        Product productHalf = Product.builder()
                .stockBySizes(Map.of("S", 1, "M", 0))
                .build();

        // When & Then
        assertEquals(1.0, productFull.getStockRatio());
        assertEquals(0.0, productEmpty.getStockRatio());
        assertEquals(0.5, productHalf.getStockRatio());
    }

    @Test
    void shouldHandleEdgeCases() {
        // Given
        Product productNoSizes = Product.builder()
                .stockBySizes(Map.of())
                .build();

        // When & Then
        assertEquals(0.0, productNoSizes.getStockRatio());
    }
}
