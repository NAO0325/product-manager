package com.product.manager.application.services;

import com.product.manager.application.ports.driven.ProductRepositoryPort;
import com.product.manager.utils.ProductDomainMocks;
import com.product.manager.domain.Product;
import com.product.manager.domain.services.ProductSortingService;
import com.product.manager.domain.valueobjects.SortingCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUseCaseTest {

    private ProductDomainMocks mocks;

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @Mock
    private ProductSortingService sortingService;

    @InjectMocks
    private ProductServiceUseCase productServiceUseCase;

    private List<Product> mockProducts;
    private List<Product> mockSortedProducts;
    private SortingCriteria mockCriteria;

    @BeforeEach
    void setUp() {
        mocks = new ProductDomainMocks();

        // Crear productos mock
        mockProducts = mocks.createMockProducts();
        mockSortedProducts = mocks.createMockSortedProducts();
        mockCriteria = mocks.createMockSortingCriteria();
    }

    @Test
    void shouldSortProductsSuccessfully() {
        // Given
        when(productRepositoryPort.findAllProductsForSorting()).thenReturn(mockProducts);
        when(sortingService.sortProducts(mockProducts, mockCriteria)).thenReturn(mockSortedProducts);

        // When
        List<Product> result = productServiceUseCase.sortProducts(mockCriteria);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar que están ordenados correctamente (por sales units desc en este mock)
        assertEquals("CONTRASTING LACE T-SHIRT", result.get(0).getName()); // 650 sales
        assertEquals("V-NECH BASIC SHIRT", result.get(1).getName());        // 100 sales

        // Verificar interacciones
        verify(productRepositoryPort, times(1)).findAllProductsForSorting();
        verify(sortingService, times(1)).sortProducts(mockProducts, mockCriteria);
    }

    @Test
    void shouldHandleEmptyProductList() {
        // Given
        when(productRepositoryPort.findAllProductsForSorting()).thenReturn(Collections.emptyList());
        when(sortingService.sortProducts(Collections.emptyList(), mockCriteria))
                .thenReturn(Collections.emptyList());

        // When
        List<Product> result = productServiceUseCase.sortProducts(mockCriteria);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productRepositoryPort, times(1)).findAllProductsForSorting();
        verify(sortingService, times(1)).sortProducts(Collections.emptyList(), mockCriteria);
    }

    @Test
    void shouldPassCriteriaCorrectlyToSortingService() {
        // Given
        SortingCriteria specificCriteria = SortingCriteria.builder()
                .salesWeight(0.8)
                .stockRatioWeight(0.2)
                .build();

        when(productRepositoryPort.findAllProductsForSorting()).thenReturn(mockProducts);
        when(sortingService.sortProducts(mockProducts, specificCriteria)).thenReturn(mockSortedProducts);

        // When
        List<Product> result = productServiceUseCase.sortProducts(specificCriteria);

        // Then
        assertNotNull(result);

        // Verificar que se pasó el criterio específico
        verify(sortingService, times(1)).sortProducts(mockProducts, specificCriteria);
    }

    @Test
    void shouldHandleRepositoryException() {
        // Given
        when(productRepositoryPort.findAllProductsForSorting())
                .thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productServiceUseCase.sortProducts(mockCriteria));

        assertEquals("Database connection error", exception.getMessage());

        // Verificar que el sorting service nunca se llama si hay error en repository
        verify(productRepositoryPort, times(1)).findAllProductsForSorting();
        verify(sortingService, never()).sortProducts(any(), any());
    }

    @Test
    void shouldHandleSortingServiceException() {
        // Given
        when(productRepositoryPort.findAllProductsForSorting()).thenReturn(mockProducts);
        when(sortingService.sortProducts(mockProducts, mockCriteria))
                .thenThrow(new IllegalArgumentException("Invalid sorting criteria"));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productServiceUseCase.sortProducts(mockCriteria));

        assertEquals("Invalid sorting criteria", exception.getMessage());

        verify(productRepositoryPort, times(1)).findAllProductsForSorting();
        verify(sortingService, times(1)).sortProducts(mockProducts, mockCriteria);
    }

    @Test
    void shouldPreserveSortingServiceResult() {
        // Given - Lista con orden específico del sorting service
        List<Product> expectedOrder = Arrays.asList(
                mocks.createProduct(5L, "HIGH SALES PRODUCT", 650),
                mocks.createProduct(1L, "MEDIUM SALES PRODUCT", 100),
                mocks.createProduct(2L, "LOW SALES PRODUCT", 50)
        );

        when(productRepositoryPort.findAllProductsForSorting()).thenReturn(mockProducts);
        when(sortingService.sortProducts(mockProducts, mockCriteria)).thenReturn(expectedOrder);

        // When
        List<Product> result = productServiceUseCase.sortProducts(mockCriteria);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verificar que preserva el orden exacto del sorting service
        assertEquals("HIGH SALES PRODUCT", result.get(0).getName());
        assertEquals("MEDIUM SALES PRODUCT", result.get(1).getName());
        assertEquals("LOW SALES PRODUCT", result.get(2).getName());

        // Verificar que no modifica la lista
        assertSame(expectedOrder, result);
    }

    @Test
    void shouldHandleNullCriteria() {
        // Given
        when(productRepositoryPort.findAllProductsForSorting()).thenReturn(mockProducts);
        when(sortingService.sortProducts(mockProducts, null))
                .thenThrow(new IllegalArgumentException("Criteria cannot be null"));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productServiceUseCase.sortProducts(null));

        assertEquals("Criteria cannot be null", exception.getMessage());

        verify(productRepositoryPort, times(1)).findAllProductsForSorting();
        verify(sortingService, times(1)).sortProducts(mockProducts, null);
    }

    @Test
    void shouldWorkWithRealWorldScenario() {
        // Given - Datos del caso práctico real
        List<Product> realProducts = Arrays.asList(
                mocks.createProductWithStock(1L, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
                mocks.createProductWithStock(5L, "CONTRASTING LACE T-SHIRT", 650, 0, 1, 0),
                mocks.createProductWithStock(2L, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9)
        );

        SortingCriteria realCriteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        List<Product> expectedSorted = Arrays.asList(
                realProducts.get(1), // CONTRASTING LACE (650 sales)
                realProducts.get(0), // V-NECH BASIC (100 sales)
                realProducts.get(2)  // CONTRASTING FABRIC (50 sales)
        );

        when(productRepositoryPort.findAllProductsForSorting()).thenReturn(realProducts);
        when(sortingService.sortProducts(realProducts, realCriteria)).thenReturn(expectedSorted);

        // When
        List<Product> result = productServiceUseCase.sortProducts(realCriteria);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("CONTRASTING LACE T-SHIRT", result.get(0).getName());
        assertEquals("V-NECH BASIC SHIRT", result.get(1).getName());
        assertEquals("CONTRASTING FABRIC T-SHIRT", result.get(2).getName());
    }
}