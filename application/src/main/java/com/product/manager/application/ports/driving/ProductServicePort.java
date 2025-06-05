package com.product.manager.application.ports.driving;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;

import java.util.List;

public interface ProductServicePort {

    List<Product> sortProducts(SortingCriteria criteria);
}
