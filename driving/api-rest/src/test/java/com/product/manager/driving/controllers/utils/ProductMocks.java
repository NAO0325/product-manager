package com.product.manager.driving.controllers.utils;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.driving.controllers.models.SortProductsRequest;
import com.product.manager.driving.controllers.models.SortProductsResponse;
import com.product.manager.driving.controllers.models.SortingCriteriaWeights;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductMocks {

    public SortProductsRequest createSortProductsRequest(Double salesWeight, Double stockRatioWeight) {
        SortProductsRequest request = new SortProductsRequest();

        SortingCriteriaWeights weights = new SortingCriteriaWeights();

        if (salesWeight != null) {
            weights.setSalesUnits(salesWeight);
        }
        if (stockRatioWeight != null) {
            weights.setStockRatio(stockRatioWeight);
        }

        com.product.manager.driving.controllers.models.SortingCriteria criteria =
                new com.product.manager.driving.controllers.models.SortingCriteria();
        criteria.setWeights(weights);

        request.setCriteria(criteria);
        return request;
    }

    public List<Product> createTestProducts() {
        return Arrays.asList(
                createTestProduct(),
                createTestProductWithHighSales()
        );
    }

    public Product createTestProduct() {
        return Product.builder()
                .id(1L)
                .name("V-NECH BASIC SHIRT")
                .salesUnits(100)
                .stockBySizes(Map.of(
                        "S", 4,
                        "M", 9,
                        "L", 0
                ))
                .build();
    }

    public Product createTestProductWithHighSales() {
        return Product.builder()
                .id(5L)
                .name("CONTRASTING LACE T-SHIRT")
                .salesUnits(650)
                .stockBySizes(Map.of(
                        "S", 0,
                        "M", 1,
                        "L", 0
                ))
                .build();
    }

    public SortingCriteria createTestSortingCriteria() {
        return SortingCriteria.builder()
                .salesWeight(0.7)
                .stockRatioWeight(0.3)
                .build();
    }

    public SortProductsResponse createMockResponse() {
        SortProductsResponse response = new SortProductsResponse();
        response.setTotalProducts(2);
        response.setProducts(Arrays.asList(
                createMockProductDto("V-NECH BASIC SHIRT"),
                createMockProductDto("CONTRASTING LACE T-SHIRT")
        ));
        return response;
    }

    public com.product.manager.driving.controllers.models.Product createMockProductDto(String name) {
        com.product.manager.driving.controllers.models.Product product =
                new com.product.manager.driving.controllers.models.Product();
        product.setName(name);
        return product;
    }
}
