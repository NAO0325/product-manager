package com.product.manager.domain.services.scoring;

import com.product.manager.domain.Product;
import com.product.manager.utils.ProductDomainMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SalesUnitsScoringCriterionTest {

    private ProductDomainMocks mocks;

    @BeforeEach
    void setUp() {
        mocks = new ProductDomainMocks();
    }

    @Test
    void shouldCalculateNormalizedSalesScore() {
        // Given
        SalesUnitsScoringCriterion criterion = new SalesUnitsScoringCriterion();
        List<Product> products = List.of(
                mocks.createProduct(1L, "Product A", 100),
                mocks.createProduct(2L, "Product B", 200), // Max sales
                mocks.createProduct(3L, "Product C", 50)
        );

        // When & Then
        assertEquals(0.5, criterion.calculateScore(products.get(0), products), 0.01); // 100/200
        assertEquals(1.0, criterion.calculateScore(products.get(1), products), 0.01); // 200/200
        assertEquals(0.25, criterion.calculateScore(products.get(2), products), 0.01); // 50/200
    }

    @Test
    void shouldCalculateNormalizedSalesScoreZero() {
        // Given
        SalesUnitsScoringCriterion criterion = new SalesUnitsScoringCriterion();
        List<Product> products = List.of(
                mocks.createProduct(1L, "Product A", 0),
                mocks.createProduct(2L, "Product B", 0),
                mocks.createProduct(3L, "Product C", 0)
        );

        // When & Then
        assertEquals(0.0, criterion.calculateScore(products.get(0), products));
    }


    @Test
    void shouldHandleEmptyProductList() {

        // Given
        SalesUnitsScoringCriterion criterion = new SalesUnitsScoringCriterion();
        List<Product> products = Collections.emptyList();

        // When & Then
        assertEquals(0.0, criterion.calculateScore(Product.builder().build(), products));
    }

    @Test
    void shouldReturnCorrectCriterionName() {
        // Given
        SalesUnitsScoringCriterion salesUnitsScoringCriterion = new SalesUnitsScoringCriterion();
        StockRatioScoringCriterion stockRatioScoringCriterion = new StockRatioScoringCriterion();

        // When
        String salesUnitsScoringCriterionName = salesUnitsScoringCriterion.getCriterionName();
        String stockRatioScoringCriterionName = stockRatioScoringCriterion.getCriterionName();

        // Then
        assertEquals("SALES_UNITS", salesUnitsScoringCriterionName);
        assertEquals("STOCK_RATIO", stockRatioScoringCriterionName);
    }
}
