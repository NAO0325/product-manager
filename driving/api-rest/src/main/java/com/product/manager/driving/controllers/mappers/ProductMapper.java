package com.product.manager.driving.controllers.mappers;

import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.driving.controllers.models.SortProductsRequest;
import com.product.manager.driving.controllers.models.SortProductsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;


@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "salesWeight", source = "criteria.weights.salesUnits")
    @Mapping(target = "stockRatioWeight", source = "criteria.weights.stockRatio")
    SortingCriteria toDomain(SortProductsRequest request);

    default SortProductsResponse toResponse(List<Product> products, SortingCriteria criteria) {
        if (products == null) {
            return null;
        }

        SortProductsResponse response = new SortProductsResponse();
        response.setProducts(toProductDtoList(products));
        response.setTotalProducts(calculateTotalProducts(products));
        response.setTimestamp(getCurrentTimestamp());

        com.product.manager.driving.controllers.models.SortingCriteria appliedCriteria =
                new com.product.manager.driving.controllers.models.SortingCriteria();
        appliedCriteria.setWeights(criteriaToWeightsDto(criteria));
        response.setAppliedCriteria(appliedCriteria);

        return response;
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "salesUnits", source = "salesUnits")
    @Mapping(target = "stockBySizes", source = "stockBySizes")
    @Mapping(target = "stockRatio", source = "stockRatio")
    com.product.manager.driving.controllers.models.Product toProductDto(Product product);

    List<com.product.manager.driving.controllers.models.Product> toProductDtoList(List<Product> products);

    @Named("criteriaToWeightsDto")
    default com.product.manager.driving.controllers.models.SortingCriteriaWeights criteriaToWeightsDto(SortingCriteria criteria) {
        if (criteria == null) {
            return new com.product.manager.driving.controllers.models.SortingCriteriaWeights();
        }

        com.product.manager.driving.controllers.models.SortingCriteriaWeights weights =
                new com.product.manager.driving.controllers.models.SortingCriteriaWeights();

        if (criteria.getSalesWeight() != null) {
            weights.setSalesUnits(criteria.getSalesWeight());
        }

        if (criteria.getStockRatioWeight() != null) {
            weights.setStockRatio(criteria.getStockRatioWeight());
        }

        return weights;
    }

    @Named("criteriaToWeightsMap")
    default java.util.Map<String, Double> criteriaToWeightsMap(SortingCriteria criteria) {
        if (criteria == null) {
            return new java.util.HashMap<>();
        }

        java.util.Map<String, Double> weights = new java.util.HashMap<>();

        if (criteria.getSalesWeight() != null) {
            weights.put("sales_units", criteria.getSalesWeight());
        }

        if (criteria.getStockRatioWeight() != null) {
            weights.put("stock_ratio", criteria.getStockRatioWeight());
        }

        return weights;
    }

    @Named("calculateTotalProducts")
    default Integer calculateTotalProducts(List<Product> products) {
        return products != null ? products.size() : 0;
    }

    default OffsetDateTime getCurrentTimestamp() {
        return OffsetDateTime.now().withNano(0).withOffsetSameInstant(ZoneOffset.UTC);
    }
}