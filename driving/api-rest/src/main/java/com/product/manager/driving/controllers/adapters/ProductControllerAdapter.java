package com.product.manager.driving.controllers.adapters;

import com.product.manager.application.ports.driving.ProductServicePort;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.driving.controllers.api.ProductSortingApi;
import com.product.manager.driving.controllers.models.SortProductsRequest;
import com.product.manager.driving.controllers.models.SortProductsResponse;
import com.product.manager.driving.controllers.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductControllerAdapter implements ProductSortingApi {

    private final ProductServicePort productServicePort;

    private final ProductMapper mapper;

    @Override
    public ResponseEntity<SortProductsResponse> sortProducts(SortProductsRequest sortProductsRequest) {

        SortingCriteria criteria = mapper.toDomain(sortProductsRequest);

        SortProductsResponse response = mapper.toResponse(productServicePort.sortProducts(criteria), criteria);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
