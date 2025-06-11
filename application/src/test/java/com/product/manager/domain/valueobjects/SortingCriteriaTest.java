package com.product.manager.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingCriteriaTest {

    @Test
    void shouldCreateSortingCriteriaWithBuilder() {
        // Given & When
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        // Then
        assertNotNull(criteria);
        assertEquals(0.7, criteria.getSalesWeight());
        assertEquals(0.3, criteria.getStockRatioWeight());
    }

    @Test
    void shouldCreateSortingCriteriaWithConstructor() {
        // Given & When
        SortingCriteria criteria = new SortingCriteria(0.6, 0.4);

        // Then
        assertNotNull(criteria);
        assertEquals(0.6, criteria.getSalesWeight());
        assertEquals(0.4, criteria.getStockRatioWeight());
    }

    @Test
    void shouldCreateWithNoArgsConstructor() {
        // Given & When
        SortingCriteria criteria = new SortingCriteria();
        criteria.setSalesWeight(0.8);
        criteria.setStockRatioWeight(0.2);

        // Then
        assertEquals(0.8, criteria.getSalesWeight());
        assertEquals(0.2, criteria.getStockRatioWeight());
    }

    @Test
    void shouldBeValidWithPositiveWeights() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        // When & Then
        assertTrue(criteria.isValid());
    }

    @Test
    void shouldBeValidWithZeroWeights() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.0)
                .stockRatioWeight(0.0)
                .build();

        // When & Then
        assertTrue(criteria.isValid());
    }

    @Test
    void shouldBeValidWithOnlyOneWeightPositive() {
        // Given
        SortingCriteria criteria1 = SortingCriteria.builder()
                .salesWeight(1.0)
                .stockRatioWeight(0.0)
                .build();

        SortingCriteria criteria2 = SortingCriteria.builder()
                .salesWeight(0.0)
                .stockRatioWeight(1.0)
                .build();

        // When & Then
        assertTrue(criteria1.isValid());
        assertTrue(criteria2.isValid());
    }

    @Test
    void shouldBeInvalidWithNullSalesWeight() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(null)
                .stockRatioWeight(0.3)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldBeInvalidWithNullStockRatioWeight() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(null)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldBeInvalidWithBothWeightsNull() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(null)
                .stockRatioWeight(null)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldBeInvalidWithNegativeSalesWeight() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(-0.1)
                .stockRatioWeight(0.3)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldBeInvalidWithPositiveSalesWeight() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(2.0)
                .stockRatioWeight(0.3)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldBeInvalidWithNegativeStockRatioWeight() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(-0.3)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldBeInvalidWithPositiveStockRatioWeight() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(2.0)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldBeInvalidWithBothWeightsNegative() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(-0.5)
                .stockRatioWeight(-0.2)
                .build();

        // When & Then
        assertFalse(criteria.isValid());
    }

    @Test
    void shouldHandleEqualsAndHashCode() {
        // Given
        SortingCriteria criteria1 = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        SortingCriteria criteria2 = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        SortingCriteria criteria3 = SortingCriteria.builder()
                .salesWeight(0.6)
                .stockRatioWeight(0.4)
                .build();

        // Then
        assertEquals(criteria1, criteria2);
        assertNotEquals(criteria1, criteria3);
        assertEquals(criteria1.hashCode(), criteria2.hashCode());
    }

    @Test
    void shouldHandleToString() {
        // Given
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();

        // When
        String result = criteria.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("0.7"));
        assertTrue(result.contains("0.3"));
    }
}