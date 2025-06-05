package com.product.manager.driven.repositories.adapters;

import com.product.manager.application.ports.driven.ProductRepositoryPort;
import com.product.manager.domain.Product;
import com.product.manager.driven.repositories.ProductJpaRepository;
import com.product.manager.driven.repositories.mappers.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository repository;

    private final ProductEntityMapper mapper;

    @Override
    public List<Product> findAllProductsForSorting() {
        return repository.findAllWithStocks().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
