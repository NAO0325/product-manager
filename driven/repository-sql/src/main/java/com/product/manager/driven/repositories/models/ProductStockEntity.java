package com.product.manager.driven.repositories.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Builder
@Table(name = "PRODUCT_STOCK")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductStockId.class)
public class ProductStockEntity implements Serializable {

    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Id
    @Column(name = "SIZE", nullable = false, length = 2)
    private String size;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ProductEntity product;
}
