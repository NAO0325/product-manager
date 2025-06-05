package com.product.manager.driven.repositories.utils;

import com.product.manager.domain.Product;
import com.product.manager.driven.repositories.models.ProductEntity;
import com.product.manager.driven.repositories.models.ProductStockEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductRepositoryMocks {

    public List<ProductEntity> createMockProductEntities() {
        // Producto 1: V-NECH BASIC SHIRT
        ProductEntity product1 = ProductEntity.builder()
                .id(1L)
                .name("V-NECH BASIC SHIRT")
                .salesUnits(100)
                .stocks(Arrays.asList(
                        createProductStock(1L, "S", 4),
                        createProductStock(1L, "M", 9),
                        createProductStock(1L, "L", 0)
                ))
                .build();

        // Producto 2: CONTRASTING FABRIC T-SHIRT
        ProductEntity product2 = ProductEntity.builder()
                .id(2L)
                .name("CONTRASTING FABRIC T-SHIRT")
                .salesUnits(50)
                .stocks(Arrays.asList(
                        createProductStock(2L, "S", 35),
                        createProductStock(2L, "M", 9),
                        createProductStock(2L, "L", 9)
                ))
                .build();

        return Arrays.asList(product1, product2);
    }

    public ProductStockEntity createProductStock(Long productId, String size, Integer quantity) {
        return ProductStockEntity.builder()
                .productId(productId)
                .size(size)
                .quantity(quantity)
                .build();
    }

    public List<Product> createMockProducts() {
        // Producto 1
        Product product1 = Product.builder()
                .id(1L)
                .name("V-NECH BASIC SHIRT")
                .salesUnits(100)
                .stockBySizes(Map.of(
                        "S", 4,
                        "M", 9,
                        "L", 0
                ))
                .build();

        // Producto 2
        Product product2 = Product.builder()
                .id(2L)
                .name("CONTRASTING FABRIC T-SHIRT")
                .salesUnits(50)
                .stockBySizes(Map.of(
                        "S", 35,
                        "M", 9,
                        "L", 9
                ))
                .build();

        return Arrays.asList(product1, product2);
    }
}
