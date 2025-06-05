package com.product.manager.driven.repositories.adapters;

import com.product.manager.domain.Product;
import com.product.manager.driven.repositories.ProductJpaRepository;
import com.product.manager.driven.repositories.mappers.ProductEntityMapper;
import com.product.manager.driven.repositories.models.ProductEntity;
import com.product.manager.driven.repositories.utils.ProductRepositoryMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {

    private ProductRepositoryMocks mocks;

    @Mock
    private ProductJpaRepository repository;

    @Mock
    private ProductEntityMapper mapper;

    @InjectMocks
    ProductRepositoryAdapter productRepositoryAdapter;

    @BeforeEach
    void setUp() {
        mocks = new ProductRepositoryMocks();
    }

    @Test
    void shouldFindAllProductsForSorting() {
        // Given
        List<ProductEntity> mockProductEntities = mocks.createMockProductEntities();
        List<Product> mockProducts = mocks.createMockProducts();

        when(repository.findAllWithStocks()).thenReturn(mockProductEntities);
        when(mapper.toDomain(mockProductEntities.get(0))).thenReturn(mockProducts.get(0));
        when(mapper.toDomain(mockProductEntities.get(1))).thenReturn(mockProducts.get(1));

        // When
        List<Product> result = productRepositoryAdapter.findAllProductsForSorting();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar primer producto
        Product firstProduct = result.get(0);
        assertEquals(1L, firstProduct.getId());
        assertEquals("V-NECH BASIC SHIRT", firstProduct.getName());
        assertEquals(100, firstProduct.getSalesUnits());

        // Verificar segundo producto
        Product secondProduct = result.get(1);
        assertEquals(2L, secondProduct.getId());
        assertEquals("CONTRASTING FABRIC T-SHIRT", secondProduct.getName());
        assertEquals(50, secondProduct.getSalesUnits());

        // Verificar interacciones
        verify(repository, times(1)).findAllWithStocks();
        verify(mapper, times(1)).toDomain(mockProductEntities.get(0));
        verify(mapper, times(1)).toDomain(mockProductEntities.get(1));
    }

    @Test
    void shouldHandleEmptyProductList() {
        // Given
        when(repository.findAllWithStocks()).thenReturn(Collections.emptyList());

        // When
        List<Product> result = productRepositoryAdapter.findAllProductsForSorting();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findAllWithStocks();
    }

    @Test
    void shouldVerifyStockDataIsCorrectlyMapped() {
        // Given
        List<ProductEntity> mockProductEntities = mocks.createMockProductEntities();
        List<Product> mockProducts = mocks.createMockProducts();

        when(repository.findAllWithStocks()).thenReturn(mockProductEntities);
        when(mapper.toDomain(mockProductEntities.get(0))).thenReturn(mockProducts.get(0));

        // When
        List<Product> result = productRepositoryAdapter.findAllProductsForSorting();

        // Then
        Product productWithStock = result.get(0);
        Map<String, Integer> stockBySizes = productWithStock.getStockBySizes();

        assertNotNull(stockBySizes);
        assertEquals(4, stockBySizes.get("S"));
        assertEquals(9, stockBySizes.get("M"));
        assertEquals(0, stockBySizes.get("L"));

        assertEquals(0.67, productWithStock.getStockRatio(), 0.01);
    }

}
