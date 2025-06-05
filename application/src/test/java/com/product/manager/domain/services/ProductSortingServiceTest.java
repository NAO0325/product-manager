package com.product.manager.domain.services;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.utils.ProductDomainMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductSortingServiceTest {

    private ProductDomainMocks mocks;

    private ProductSortingService sortingService;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        mocks = new ProductDomainMocks();

        sortingService = new ProductSortingService();
        products = mocks.createTestProducts();
    }

    @Test
    void shouldSortProductsBySalesOnly() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(1.0)
                .stockRatioWeight(0.0)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(products, criteria);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verificar orden descendente por ventas: 650 > 100 > 50
        assertEquals("CONTRASTING LACE T-SHIRT", result.get(0).getName());    // 650 sales
        assertEquals("V-NECH BASIC SHIRT", result.get(1).getName());         // 100 sales
        assertEquals("CONTRASTING FABRIC T-SHIRT", result.get(2).getName()); // 50 sales
    }

    @Test
    void shouldSortProductsByStockRatioOnly() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.0)
                .stockRatioWeight(1.0)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(products, criteria);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verificar orden descendente por stock ratio
        // CONTRASTING FABRIC: 3/3 = 1.0
        // V-NECH BASIC: 2/3 = 0.67
        // CONTRASTING LACE: 1/3 = 0.33
        assertEquals("CONTRASTING FABRIC T-SHIRT", result.get(0).getName());
        assertEquals("V-NECH BASIC SHIRT", result.get(1).getName());
        assertEquals("CONTRASTING LACE T-SHIRT", result.get(2).getName());
    }

    @Test
    void shouldSortProductsByWeightedCriteria() {
        // Given - Caso práctico: 70% ventas, 30% stock ratio
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(products, criteria);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // CONTRASTING LACE debería estar primero (ventas muy altas compensan stock bajo)
        assertEquals("CONTRASTING LACE T-SHIRT", result.get(0).getName());

        // Los otros dos pueden variar según el balance de criterios
        assertTrue(Arrays.asList("V-NECH BASIC SHIRT", "CONTRASTING FABRIC T-SHIRT")
                .contains(result.get(1).getName()));
    }

    @Test
    void shouldCalculateScoresCorrectly() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.5)
                .stockRatioWeight(0.5)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(products, criteria);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verificar que la lista está ordenada (score descendente)
        // No verificamos valores exactos, sino que el orden es correcto
        for (int i = 0; i < result.size() - 1; i++) {
            // Cada producto debe tener score >= al siguiente
            double currentScore = calculateExpectedScore(result.get(i), criteria, getMaxSales());
            double nextScore = calculateExpectedScore(result.get(i + 1), criteria, getMaxSales());
            assertTrue(currentScore >= nextScore,
                    String.format("Product %s (score: %.3f) should have higher score than %s (score: %.3f)",
                            result.get(i).getName(), currentScore,
                            result.get(i + 1).getName(), nextScore));
        }
    }

    @Test
    void shouldHandleEmptyProductList() {
        // Given
        List<Product> emptyList = Collections.emptyList();
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(emptyList, criteria);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandleSingleProduct() {
        // Given
        List<Product> singleProduct = Collections.singletonList(products.get(0));
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(singleProduct, criteria);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(products.get(0).getName(), result.get(0).getName());
    }

    @Test
    void shouldThrowExceptionForInvalidCriteria() {
        // Given
        SortingCriteria invalidCriteria = SortingCriteria.builder()
                .salesWeight(-0.1)
                .stockRatioWeight(0.3)
                .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sortingService.sortProducts(products, invalidCriteria));

        assertEquals("Invalid sorting criteria", exception.getMessage());
    }

    @Test
    void shouldHandleNullCriteria() {
        // When & Then
        assertThrows(NullPointerException.class,
                () -> sortingService.sortProducts(products, null));
    }

    @Test
    void shouldHandleProductsWithSameSales() {
        // Given - Productos con mismas ventas
        List<Product> sameProducts = Arrays.asList(
                mocks.createProduct(1L, "Product A", 100, Map.of("S", 10, "M", 0, "L", 0)),
                mocks.createProduct(2L, "Product B", 100, Map.of("S", 0, "M", 10, "L", 10)),
                mocks.createProduct(3L, "Product C", 100, Map.of("S", 5, "M", 5, "L", 5))
        );

        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.0)
                .stockRatioWeight(1.0)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(sameProducts, criteria);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // Product B y C deberían estar antes que A (mejor stock ratio)
        assertEquals("Product C", result.get(0).getName()); // ratio 3/3 = 1.0
        assertEquals("Product B", result.get(1).getName()); // ratio 2/3 = 0.67
        assertEquals("Product A", result.get(2).getName()); // ratio 1/3 = 0.33
    }

    @Test
    void shouldHandleProductsWithZeroSales() {
        // Given
        List<Product> productsWithZero = Arrays.asList(
                mocks.createProduct(1L, "Zero Sales", 0, Map.of("S", 10, "M", 10, "L", 10)),
                mocks.createProduct(2L, "Some Sales", 50, Map.of("S", 0, "M", 0, "L", 0))
        );

        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.8)
                .stockRatioWeight(0.2)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(productsWithZero, criteria);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Some Sales", result.get(0).getName()); // Mayor ventas gana
    }

    @Test
    void shouldNotModifyOriginalList() {
        // Given
        List<Product> originalProducts = new ArrayList<>(products);
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        // When
        List<Product> result = sortingService.sortProducts(products, criteria);

        // Then
        assertEquals(originalProducts, products); // Lista original no modificada
        assertNotSame(result, products); // Es una nueva lista
    }

    private double calculateExpectedScore(Product product, SortingCriteria criteria, int maxSales) {
        double salesScore = (double) product.getSalesUnits() / maxSales;
        double stockRatioScore = product.getStockRatio();
        return (salesScore * criteria.getSalesWeight()) +
                (stockRatioScore * criteria.getStockRatioWeight());
    }

    private int getMaxSales() {
        return products.stream()
                .mapToInt(Product::getSalesUnits)
                .max()
                .orElse(1);
    }
}
