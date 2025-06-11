package com.product.manager.domain.valueobjects;

import com.product.manager.domain.Product;
import com.product.manager.domain.services.scoring.ScoringCriterion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeightedScoringCriterionTest {

    @Mock
    private ScoringCriterion mockCriterion;

    @Mock
    private Product mockProduct;

    @Test
    void shouldCreateWeightedScoringCriterionWithConstructor() {
        // Given & When
        WeightedScoringCriterion weightedCriterion = new WeightedScoringCriterion(mockCriterion, 0.5);

        // Then
        assertNotNull(weightedCriterion);
        assertEquals(mockCriterion, weightedCriterion.getCriterion());
        assertEquals(0.5, weightedCriterion.getWeight());
    }

    @Test
    void shouldCreateWeightedScoringCriterionWithBuilder() {
        // Given & When
        WeightedScoringCriterion weightedCriterion = WeightedScoringCriterion.builder()
                .criterion(mockCriterion)
                .weight(0.7)
                .build();

        // Then
        assertNotNull(weightedCriterion);
        assertEquals(mockCriterion, weightedCriterion.getCriterion());
        assertEquals(0.7, weightedCriterion.getWeight());
    }

    @Test
    void shouldThrowExceptionWhenCriterionIsNull() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () ->
                new WeightedScoringCriterion(null, 0.5));
    }

    @Test
    void shouldThrowExceptionWhenWeightIsNegative() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () ->
                new WeightedScoringCriterion(mockCriterion, -0.1));
    }

    @Test
    void shouldThrowExceptionWhenWeightIsGreaterThanOne() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () ->
                new WeightedScoringCriterion(mockCriterion, 1.1));
    }

    @Test
    void shouldCalculateWeightedScore() {
        // Given
        double criterionScore = 0.8;
        double weight = 0.5;
        List<Product> allProducts = Collections.singletonList(mockProduct);

        WeightedScoringCriterion weightedCriterion = new WeightedScoringCriterion(mockCriterion, weight);
        when(mockCriterion.calculateScore(mockProduct, allProducts)).thenReturn(criterionScore);

        // When
        double result = weightedCriterion.calculateWeightedScore(mockProduct, allProducts);

        // Then
        assertEquals(criterionScore * weight, result);
    }
}