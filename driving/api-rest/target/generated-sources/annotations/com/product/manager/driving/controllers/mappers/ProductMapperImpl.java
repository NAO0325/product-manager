package com.product.manager.driving.controllers.mappers;

import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.domain.valueobjects.SortingCriteria.SortingCriteriaBuilder;
import com.product.manager.driving.controllers.models.Product;
import com.product.manager.driving.controllers.models.SortProductsRequest;
import com.product.manager.driving.controllers.models.SortingCriteriaWeights;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T09:53:32+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.15 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public SortingCriteria toDomain(SortProductsRequest request) {
        if ( request == null ) {
            return null;
        }

        SortingCriteriaBuilder sortingCriteria = SortingCriteria.builder();

        sortingCriteria.salesWeight( requestCriteriaWeightsSalesUnits( request ) );
        sortingCriteria.stockRatioWeight( requestCriteriaWeightsStockRatio( request ) );

        return sortingCriteria.build();
    }

    @Override
    public Product toProductDto(com.product.manager.domain.Product product) {
        if ( product == null ) {
            return null;
        }

        Product product1 = new Product();

        product1.setId( product.getId() );
        product1.setName( product.getName() );
        product1.setSalesUnits( product.getSalesUnits() );
        Map<String, Integer> map = product.getStockBySizes();
        if ( map != null ) {
            product1.setStockBySizes( new HashMap<String, Integer>( map ) );
        }
        product1.setStockRatio( product.getStockRatio() );

        return product1;
    }

    @Override
    public List<Product> toProductDtoList(List<com.product.manager.domain.Product> products) {
        if ( products == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( products.size() );
        for ( com.product.manager.domain.Product product : products ) {
            list.add( toProductDto( product ) );
        }

        return list;
    }

    private Double requestCriteriaWeightsSalesUnits(SortProductsRequest sortProductsRequest) {
        if ( sortProductsRequest == null ) {
            return null;
        }
        com.product.manager.driving.controllers.models.SortingCriteria criteria = sortProductsRequest.getCriteria();
        if ( criteria == null ) {
            return null;
        }
        SortingCriteriaWeights weights = criteria.getWeights();
        if ( weights == null ) {
            return null;
        }
        Double salesUnits = weights.getSalesUnits();
        if ( salesUnits == null ) {
            return null;
        }
        return salesUnits;
    }

    private Double requestCriteriaWeightsStockRatio(SortProductsRequest sortProductsRequest) {
        if ( sortProductsRequest == null ) {
            return null;
        }
        com.product.manager.driving.controllers.models.SortingCriteria criteria = sortProductsRequest.getCriteria();
        if ( criteria == null ) {
            return null;
        }
        SortingCriteriaWeights weights = criteria.getWeights();
        if ( weights == null ) {
            return null;
        }
        Double stockRatio = weights.getStockRatio();
        if ( stockRatio == null ) {
            return null;
        }
        return stockRatio;
    }
}
