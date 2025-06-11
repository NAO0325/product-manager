package com.product.manager.domain.valueobjects;

import com.product.manager.domain.Product;
import com.product.manager.domain.services.scoring.ScoringCriterion;
import lombok.*;

import java.util.List;

/**
 * Value Object que encapsula un criterio con su peso
 */
@Getter
@NoArgsConstructor
@Builder
public class WeightedScoringCriterion {

    private ScoringCriterion criterion;
    private double weight;

    public WeightedScoringCriterion(ScoringCriterion criterion, double weight) {

        if (criterion == null) {
            throw new IllegalArgumentException("Scoring criterion cannot be null");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        if (weight > 1) {
            throw new IllegalArgumentException("Weight cannot be more than 1");
        }

        this.criterion = criterion;
        this.weight = weight;
    }

    /**
     * Calcula la puntuación ponderada para un producto
     */
    public double calculateWeightedScore(Product product, List<Product> allProducts) {
        return criterion.calculateScore(product, allProducts) * weight;
    }
}