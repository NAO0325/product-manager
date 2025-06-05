package com.product.manager.driven.repositories.mappers;

import com.product.manager.domain.Product;
import com.product.manager.domain.Product.ProductBuilder;
import com.product.manager.driven.repositories.models.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T09:53:35+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.15 (Eclipse Adoptium)"
)
@Component
public class ProductEntityMapperImpl implements ProductEntityMapper {

    @Override
    public Product toDomain(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        product.stockBySizes( stocksToStockBySizes( entity.getStocks() ) );
        product.id( entity.getId() );
        product.name( entity.getName() );
        product.salesUnits( entity.getSalesUnits() );

        return product.build();
    }
}
