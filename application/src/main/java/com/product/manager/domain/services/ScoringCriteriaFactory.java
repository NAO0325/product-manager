package com.product.manager.domain.services;

import com.product.manager.domain.services.scoring.SalesUnitsScoringCriterion;
import com.product.manager.domain.services.scoring.ScoringCriterion;
import com.product.manager.domain.services.scoring.StockRatioScoringCriterion;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.domain.valueobjects.WeightedScoringCriterion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Factory para crear criterios de puntuación ponderados
 */
public class ScoringCriteriaFactory {

    private final Map<String, ScoringCriterion> availableCriteria;

    public ScoringCriteriaFactory() {

        // Inicializar criterios disponibles
        this.availableCriteria = Map.of(
                "SALES_UNITS", new SalesUnitsScoringCriterion(),
                "STOCK_RATIO", new StockRatioScoringCriterion()
        );
    }

    /**
     * Crea lista de criterios ponderados basándose en SortingCriteria
     */
    public List<WeightedScoringCriterion> createWeightedCriteria(SortingCriteria sortingCriteria) {

        List<WeightedScoringCriterion> weightedCriteria = new ArrayList<>();

        // Sales Units Criterion
        if (sortingCriteria.getSalesWeight() > 0) {
            ScoringCriterion salesCriterion = availableCriteria.get("SALES_UNITS");
            weightedCriteria.add(new WeightedScoringCriterion(salesCriterion, sortingCriteria.getSalesWeight()));
        }

        // Stock Ratio Criterion
        if (sortingCriteria.getStockRatioWeight() > 0) {
            ScoringCriterion stockCriterion = availableCriteria.get("STOCK_RATIO");
            weightedCriteria.add(new WeightedScoringCriterion(stockCriterion, sortingCriteria.getStockRatioWeight()));
        }

        return weightedCriteria;
    }
}
