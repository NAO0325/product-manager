package com.product.manager.driven.repositories;

import com.product.manager.driven.repositories.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
        SELECT DISTINCT p
        FROM ProductEntity p
        LEFT JOIN FETCH p.stocks
        ORDER BY p.id
        """)
    List<ProductEntity> findAllWithStocks();
}
