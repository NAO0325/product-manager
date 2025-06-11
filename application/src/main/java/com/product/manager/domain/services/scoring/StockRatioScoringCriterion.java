package com.product.manager.domain.services.scoring;

import com.product.manager.domain.Product;

import java.util.List;

/**
 * Criterio de puntuación basado en ratio de stock
 */
public class StockRatioScoringCriterion implements ScoringCriterion {

    @Override
    public double calculateScore(Product product, List<Product> allProducts) {
        return product.getStockRatio(); // Ya está normalizado entre 0-1
    }

    @Override
    public String getCriterionName() {
        return "STOCK_RATIO";
    }
}
