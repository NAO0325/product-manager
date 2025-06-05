package com.product.manager.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortingCriteria {

    private Double salesWeight;      // Peso para criterio de ventas
    private Double stockRatioWeight; // Peso para criterio de ratio de stock

    public boolean isValid() {
        return salesWeight != null && (salesWeight >= 0 && salesWeight <= 1) &&
                stockRatioWeight != null && (stockRatioWeight >= 0 && stockRatioWeight <= 1);
    }
}
