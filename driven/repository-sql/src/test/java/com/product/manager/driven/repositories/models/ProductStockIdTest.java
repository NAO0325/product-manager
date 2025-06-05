package com.product.manager.driven.repositories.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockIdTest {

    @Test
    void shouldCreateProductStockId() {
        // Given & When
        ProductStockId stockId = new ProductStockId(1L, "M");

        // Then
        assertNotNull(stockId);
        assertEquals(1L, stockId.getProductId());
        assertEquals("M", stockId.getSize());
    }

    @Test
    void shouldHandleEqualsCorrectly() {
        // Given
        ProductStockId id1 = new ProductStockId(1L, "S");
        ProductStockId id2 = new ProductStockId(1L, "S");
        ProductStockId id3 = new ProductStockId(1L, "M");
        ProductStockId id4 = new ProductStockId(2L, "S");

        // Then
        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertNotEquals(id1, id4);
        assertNotEquals(null, id1);
        assertNotEquals("not a ProductStockId", id1);
    }

    @Test
    void shouldHandleHashCodeCorrectly() {
        // Given
        ProductStockId id1 = new ProductStockId(1L, "S");
        ProductStockId id2 = new ProductStockId(1L, "S");
        ProductStockId id3 = new ProductStockId(1L, "M");

        // Then
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }

    @Test
    void shouldCreateWithNoArgsConstructor() {
        // Given & When
        ProductStockId stockId = new ProductStockId();
        stockId.setProductId(1L);
        stockId.setSize("L");

        // Then
        assertEquals(1L, stockId.getProductId());
        assertEquals("L", stockId.getSize());
    }
}