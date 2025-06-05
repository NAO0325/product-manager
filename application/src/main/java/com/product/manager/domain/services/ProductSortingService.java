package com.product.manager.domain.services;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;

import java.util.Comparator;
import java.util.List;

public class ProductSortingService {

    public List<Product> sortProducts(List<Product> products, SortingCriteria criteria) {

        if (!criteria.isValid()) {
            throw new IllegalArgumentException("Invalid sorting criteria");
        }

        // Calcular valor máximo de ventas para normalización
        int maxSales = products.stream()
                .mapToInt(Product::getSalesUnits)
                .max()
                .orElse(1);

        // Ordenar por puntuación ponderada (descendente)
        return products.stream()
                .sorted(Comparator.comparingDouble(
                        (Product p) -> calculateScore(p, criteria, maxSales)
                ).reversed())
                .toList();
    }

    private Double calculateScore(Product product, SortingCriteria criteria, int maxSales) {

        // Criterio 1: Normalizar ventas (0-1)
        double salesScore = (double) product.getSalesUnits() / maxSales;

        // Criterio 2: Ratio de stock (ya está normalizado 0-1)
        double stockRatioScore = product.getStockRatio();

        // Suma ponderada
        return (salesScore * criteria.getSalesWeight()) +
                (stockRatioScore * criteria.getStockRatioWeight());
    }
}
