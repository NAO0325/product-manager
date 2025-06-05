package com.product.manager.driven.repositories.mappers;

import com.product.manager.domain.Product;
import com.product.manager.driven.repositories.models.ProductEntity;
import com.product.manager.driven.repositories.models.ProductStockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    @Mapping(target = "stockBySizes", source = "stocks", qualifiedByName = "stocksToStockBySizes")
    Product toDomain(ProductEntity entity);

    @Named("stocksToStockBySizes")
    default Map<String, Integer> stocksToStockBySizes(List<ProductStockEntity> stocks) {

        if (CollectionUtils.isEmpty(stocks)) {
            return new HashMap<>();
        }

        return stocks.stream()
                .collect(Collectors.toMap(
                        ProductStockEntity::getSize,
                        ProductStockEntity::getQuantity,
                        (existing, replacement) -> replacement
                ));
    }

}