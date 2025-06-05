package com.product.manager.driven.repositories.mappers;

import com.product.manager.domain.Product;
import com.product.manager.driven.repositories.models.ProductEntity;
import com.product.manager.driven.repositories.models.ProductStockEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityMapperTest {

    private final ProductEntityMapper mapper = Mappers.getMapper(ProductEntityMapper.class);

    @Test
    void shouldMapProductEntityToProduct() {
        // Given
        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .name("V-NECH BASIC SHIRT")
                .salesUnits(100)
                .stocks(Arrays.asList(
                        createStock(1L, "S", 4),
                        createStock(1L, "M", 9),
                        createStock(1L, "L", 0)
                ))
                .build();

        // When
        Product result = mapper.toDomain(entity);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("V-NECH BASIC SHIRT", result.getName());
        assertEquals(100, result.getSalesUnits());

        Map<String, Integer> expectedStock = Map.of("S", 4, "M", 9, "L", 0);
        assertEquals(expectedStock, result.getStockBySizes());
    }

    @Test
    void shouldHandleNullStocks() {
        // Given
        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .name("Product without stocks")
                .salesUnits(50)
                .stocks(null)
                .build();

        // When
        Product result = mapper.toDomain(entity);

        // Then
        assertNotNull(result);
        assertNotNull(result.getStockBySizes());
        assertTrue(result.getStockBySizes().isEmpty());
    }

    @Test
    void shouldHandleEmptyStocks() {
        // Given
        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .name("Product with empty stocks")
                .salesUnits(50)
                .stocks(Collections.emptyList())
                .build();

        // When
        Product result = mapper.toDomain(entity);

        // Then
        assertNotNull(result);
        assertNotNull(result.getStockBySizes());
        assertTrue(result.getStockBySizes().isEmpty());
    }

    @Test
    void shouldMapStocksToStockBySizes() {
        // Given - Test directo del método @Named
        List<ProductStockEntity> stocks = Arrays.asList(
                createStock(1L, "S", 10),
                createStock(1L, "M", 20),
                createStock(1L, "L", 0)
        );

        // When
        Map<String, Integer> result = mapper.stocksToStockBySizes(stocks);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(10, result.get("S"));
        assertEquals(20, result.get("M"));
        assertEquals(0, result.get("L"));
    }

    @Test
    void shouldHandleDuplicateSizesInStocks() {
        // Given
        List<ProductStockEntity> stocks = Arrays.asList(
                createStock(1L, "S", 10),
                createStock(1L, "M", 20),
                createStock(1L, "S", 15),
                createStock(1L, "L", 5),
                createStock(1L, "M", 25)
        );

        // When
        Map<String, Integer> result = mapper.stocksToStockBySizes(stocks);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals(15, result.get("S"));
        assertEquals(25, result.get("M"));
        assertEquals(5, result.get("L"));
    }

    private ProductStockEntity createStock(Long productId, String size, Integer quantity) {
        return ProductStockEntity.builder()
                .productId(productId)
                .size(size)
                .quantity(quantity)
                .build();
    }
}