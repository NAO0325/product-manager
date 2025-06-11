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
        return isValidWeight(salesWeight) &&
                isValidWeight(stockRatioWeight); // Añadir validación para cada peso por criterio
    }

    private boolean isValidWeight(Double weight) {
        return weight != null && (weight >= 0 && weight <= 1);
    }
}
