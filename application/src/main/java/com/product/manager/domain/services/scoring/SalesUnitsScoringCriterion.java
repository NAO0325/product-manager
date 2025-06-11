package com.product.manager.domain.services.scoring;

import com.product.manager.domain.Product;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Criterio de puntuación basado en unidades vendidas
 */
public class SalesUnitsScoringCriterion implements ScoringCriterion {

    @Override
    public double calculateScore(Product product, List<Product> allProducts) {

        if (CollectionUtils.isEmpty(allProducts)) {
            return 0.0;
        }

        int maxSales = allProducts.stream()
                .mapToInt(Product::getSalesUnits)
                .max()
                .orElse(1);

        return maxSales > 0 ? (double) product.getSalesUnits() / maxSales : 0.0;
    }

    @Override
    public String getCriterionName() {
        return "SALES_UNITS";
    }
}
