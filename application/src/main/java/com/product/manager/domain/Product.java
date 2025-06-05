package com.product.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private Long id;

    private String name;

    private Integer salesUnits;

    private Map<String, Integer> stockBySizes;

    public Double getStockRatio() {
        int totalSizes = stockBySizes.size();
        int availableSizes = (int) stockBySizes.values().stream()
                .filter(stock -> stock > 0)
                .count();

        if (totalSizes == 0) {
            return 0.0;
        }

        double ratio = (double) availableSizes / totalSizes;

        // Redondear a 2 decimales con Math.round
        return Math.round(ratio * 100.0) / 100.0;
    }
}
