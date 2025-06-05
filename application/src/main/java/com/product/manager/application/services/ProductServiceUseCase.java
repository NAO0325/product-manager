package com.product.manager.application.services;


import com.product.manager.application.ports.driven.ProductRepositoryPort;
import com.product.manager.application.ports.driving.ProductServicePort;
import com.product.manager.domain.Product;
import com.product.manager.domain.services.ProductSortingService;
import com.product.manager.domain.valueobjects.SortingCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceUseCase implements ProductServicePort {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductSortingService sortingService;

    @Override
    public List<Product> sortProducts(SortingCriteria criteria) {

        // 1. Obtener productos con stock
        List<Product> products = productRepositoryPort.findAllProductsForSorting();

        // 2. Aplicar ordenación
        return sortingService.sortProducts(products, criteria);
    }

}

