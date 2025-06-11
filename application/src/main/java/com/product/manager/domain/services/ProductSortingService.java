package com.product.manager.domain.services;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.domain.valueobjects.WeightedScoringCriterion;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductSortingService {

    private final ScoringCriteriaFactory criteriaFactory;

    public ProductSortingService() {
        this.criteriaFactory = new ScoringCriteriaFactory();
    }

    public List<Product> sortProducts(List<Product> products, SortingCriteria criteria) {

        if (CollectionUtils.isEmpty(products)) {
            return new ArrayList<>();
        }

        if (!criteria.isValid()) {
            throw new IllegalArgumentException("Invalid sorting criteria");
        }

        List<WeightedScoringCriterion> weightedCriteria = criteriaFactory.createWeightedCriteria(criteria);


        return products.stream()
                .sorted(Comparator.comparingDouble(
                        (Product p) -> calculateTotalScore(p, products, weightedCriteria)
                ).reversed())
                .toList();
    }

    /**
     * Calcula la puntuación total combinando todos los criterios ponderados
     */
    private double calculateTotalScore(Product product, List<Product> allProducts,
                                       List<WeightedScoringCriterion> weightedCriteria) {
        return weightedCriteria.stream()
                .mapToDouble(criterion -> criterion.calculateWeightedScore(product, allProducts))
                .sum();
    }
}
