package com.product.manager.driving.controllers.mappers;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.driving.controllers.models.SortProductsRequest;
import com.product.manager.driving.controllers.models.SortProductsResponse;
import com.product.manager.driving.controllers.utils.ProductMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private ProductMocks mocks;

    private ProductMapper productMapper;

    @BeforeEach
    public void setup() {
        mocks = new ProductMocks();
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    void shouldMapSortProductsRequestToSortingCriteria() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);

        // When
        SortingCriteria result = productMapper.toDomain(request);

        // Then
        assertNotNull(result);
        assertEquals(0.7, result.getSalesWeight());
        assertEquals(0.3, result.getStockRatioWeight());
    }

    @Test
    void shouldMapSortProductsRequestWithBalancedWeights() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.5, 0.5);

        // When
        SortingCriteria result = productMapper.toDomain(request);

        // Then
        assertNotNull(result);
        assertEquals(0.5, result.getSalesWeight());
        assertEquals(0.5, result.getStockRatioWeight());
        assertTrue(result.isValid());
    }

    @Test
    void shouldMapSortProductsRequestWithZeroWeights() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.0, 0.0);

        // When
        SortingCriteria result = productMapper.toDomain(request);

        // Then
        assertNotNull(result);
        assertEquals(0.0, result.getSalesWeight());
        assertEquals(0.0, result.getStockRatioWeight());
        assertTrue(result.isValid());
    }

    @Test
    void shouldMapSortProductsRequestWithSalesOnlyWeight() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(1.0, 0.0);

        // When
        SortingCriteria result = productMapper.toDomain(request);

        // Then
        assertNotNull(result);
        assertEquals(1.0, result.getSalesWeight());
        assertEquals(0.0, result.getStockRatioWeight());
        assertTrue(result.isValid());
    }

    @Test
    void shouldHandleNullWeightsInRequest() {
        // Given
        SortProductsRequest request = new SortProductsRequest();
        com.product.manager.driving.controllers.models.SortingCriteria criteria =
                new com.product.manager.driving.controllers.models.SortingCriteria();
        criteria.setWeights(null);
        request.setCriteria(criteria);

        // When
        SortingCriteria result = productMapper.toDomain(request);

        // Then
        assertNotNull(result);
        assertNull(result.getSalesWeight());
        assertNull(result.getStockRatioWeight());
        assertFalse(result.isValid());
    }

    // ===== RESPONSE MAPPING TESTS =====

    @Test
    void shouldMapProductsToSortProductsResponse() {
        // Given
        List<Product> products = mocks.createTestProducts();
        SortingCriteria criteria = mocks.createTestSortingCriteria();

        // When
        SortProductsResponse result = productMapper.toResponse(products, criteria);

        // Then
        assertNotNull(result);
        assertNotNull(result.getProducts());
        assertEquals(2, result.getProducts().size());
        assertEquals(2, result.getTotalProducts());
        assertNotNull(result.getTimestamp());
        assertEquals(ZoneOffset.UTC, result.getTimestamp().getOffset());

        // Verificar productos mapeados
        com.product.manager.driving.controllers.models.Product firstProduct = result.getProducts().get(0);
        assertEquals(1L, firstProduct.getId());
        assertEquals("V-NECH BASIC SHIRT", firstProduct.getName());
        assertEquals(100, firstProduct.getSalesUnits());
        assertEquals(0.67, firstProduct.getStockRatio(), 0.01);

        // Verificar criteria aplicados
        assertNotNull(result.getAppliedCriteria());
        assertNotNull(result.getAppliedCriteria().getWeights());
        assertEquals(0.7, result.getAppliedCriteria().getWeights().getSalesUnits());
        assertEquals(0.3, result.getAppliedCriteria().getWeights().getStockRatio());
    }

    @Test
    void shouldMapEmptyProductListToResponse() {
        // Given
        List<Product> emptyProducts = Collections.emptyList();
        SortingCriteria criteria = mocks.createTestSortingCriteria();

        // When
        SortProductsResponse result = productMapper.toResponse(emptyProducts, criteria);

        // Then
        assertNotNull(result);
        assertNotNull(result.getProducts());
        assertTrue(result.getProducts().isEmpty());
        assertEquals(0, result.getTotalProducts());
        assertNotNull(result.getTimestamp());
        assertNotNull(result.getAppliedCriteria());
    }

    @Test
    void shouldHandleNullProductsListInResponse() {
        // Given
        SortingCriteria criteria = mocks.createTestSortingCriteria();

        // When
        SortProductsResponse result = productMapper.toResponse(null, criteria);

        // Then
        assertNull(result);
    }


    @Test
    void shouldMapSingleProductToProductDto() {
        // Given
        Product product = mocks.createTestProduct();

        // When
        com.product.manager.driving.controllers.models.Product result =
                productMapper.toProductDto(product);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("V-NECH BASIC SHIRT", result.getName());
        assertEquals(100, result.getSalesUnits());
        assertEquals(0.67, result.getStockRatio(), 0.01);

        // Verificar stock by sizes
        assertNotNull(result.getStockBySizes());
        assertEquals(4, result.getStockBySizes().get("S"));
        assertEquals(9, result.getStockBySizes().get("M"));
        assertEquals(0, result.getStockBySizes().get("L"));
    }

    @Test
    void shouldMapProductListToProductDtoList() {
        // Given
        List<Product> products = mocks.createTestProducts();

        // When
        List<com.product.manager.driving.controllers.models.Product> result =
                productMapper.toProductDtoList(products);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        com.product.manager.driving.controllers.models.Product first = result.get(0);
        assertEquals("V-NECH BASIC SHIRT", first.getName());

        com.product.manager.driving.controllers.models.Product second = result.get(1);
        assertEquals("CONTRASTING LACE T-SHIRT", second.getName());
    }

    // ===== NAMED METHODS TESTS =====

    @Test
    void shouldConvertCriteriaToWeightsDto() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.8)
                .stockRatioWeight(0.2)
                .build();

        // When
        com.product.manager.driving.controllers.models.SortingCriteriaWeights result =
                productMapper.criteriaToWeightsDto(criteria);

        // Then
        assertNotNull(result);
        assertEquals(0.8, result.getSalesUnits());
        assertEquals(0.2, result.getStockRatio());
    }

    @Test
    void shouldHandleNullCriteriaInWeightsDto() {
        // When
        com.product.manager.driving.controllers.models.SortingCriteriaWeights result =
                productMapper.criteriaToWeightsDto(null);

        // Then
        assertNotNull(result);
        assertNull(result.getSalesUnits());
        assertNull(result.getStockRatio());
    }

    @Test
    void shouldHandlePartialWeightsInCriteriaDto() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(null)
                .build();

        // When
        com.product.manager.driving.controllers.models.SortingCriteriaWeights result =
                productMapper.criteriaToWeightsDto(criteria);

        // Then
        assertNotNull(result);
        assertEquals(0.7, result.getSalesUnits());
        assertNull(result.getStockRatio());
    }

    @Test
    void shouldConvertCriteriaToWeightsMap() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.8)
                .stockRatioWeight(0.2)
                .build();

        // When
        Map<String, Double> result = productMapper.criteriaToWeightsMap(criteria);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(0.8, result.get("sales_units"));
        assertEquals(0.2, result.get("stock_ratio"));
    }

    @Test
    void shouldHandleNullCriteriaInWeightsMap() {
        // When
        Map<String, Double> result = productMapper.criteriaToWeightsMap(null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandlePartialWeightsInCriteria() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(null)
                .build();

        // When
        Map<String, Double> result = productMapper.criteriaToWeightsMap(criteria);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(0.7, result.get("sales_units"));
        assertFalse(result.containsKey("stock_ratio"));
    }

    @Test
    void shouldCalculateTotalProducts() {
        // Given
        List<Product> products = mocks.createTestProducts();

        // When
        Integer result = productMapper.calculateTotalProducts(products);

        // Then
        assertEquals(2, result);
    }

    @Test
    void shouldCalculateTotalProductsForEmptyList() {
        // Given
        List<Product> emptyProducts = Collections.emptyList();

        // When
        Integer result = productMapper.calculateTotalProducts(emptyProducts);

        // Then
        assertEquals(0, result);
    }

    @Test
    void shouldCalculateTotalProductsForNullList() {
        // When
        Integer result = productMapper.calculateTotalProducts(null);

        // Then
        assertEquals(0, result);
    }

    @Test
    void shouldGenerateCurrentTimestamp() {
        // When
        var timestamp1 = productMapper.getCurrentTimestamp();
        var timestamp2 = productMapper.getCurrentTimestamp();

        // Then
        assertNotNull(timestamp1);
        assertNotNull(timestamp2);
        assertEquals(ZoneOffset.UTC, timestamp1.getOffset());
        assertEquals(ZoneOffset.UTC, timestamp2.getOffset());
        assertEquals(0, timestamp1.getNano());
        assertEquals(0, timestamp2.getNano());

        // Los timestamps deberían ser muy cercanos (dentro del mismo segundo)
        assertTrue(Math.abs(timestamp1.toEpochSecond() - timestamp2.toEpochSecond()) <= 1);
    }
}
