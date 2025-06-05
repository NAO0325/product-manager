package com.product.manager.application.ports.driven;

import com.product.manager.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {

    List<Product> findAllProductsForSorting();
}
