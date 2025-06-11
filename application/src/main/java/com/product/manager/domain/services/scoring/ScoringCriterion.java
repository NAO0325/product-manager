package com.product.manager.domain.services.scoring;

import com.product.manager.domain.Product;

import java.util.List;

public interface ScoringCriterion {

    /**
     * Calcula la puntuación para un criterio específico
     * @param product Producto a evaluar
     * @param products Lista completa para contexto (ej: calcular máximos)
     * @return Puntuación normalizada entre 0 y 1
     */
    double calculateScore(Product product, List<Product> products);

    /**
     * @return Identificador único del criterio
     */
    String getCriterionName();

}
