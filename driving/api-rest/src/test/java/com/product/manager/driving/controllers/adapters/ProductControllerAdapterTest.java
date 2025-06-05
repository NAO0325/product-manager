package com.product.manager.driving.controllers.adapters;

import com.product.manager.application.ports.driving.ProductServicePort;
import com.product.manager.domain.Product;
import com.product.manager.domain.valueobjects.SortingCriteria;
import com.product.manager.driving.controllers.mappers.ProductMapper;
import com.product.manager.driving.controllers.models.SortProductsRequest;
import com.product.manager.driving.controllers.models.SortProductsResponse;
import com.product.manager.driving.controllers.utils.ProductMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerAdapterTest {

    private ProductMocks mocks;

    @Mock
    private ProductServicePort productServicePort;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductControllerAdapter productControllerAdapter;

    @BeforeEach
    void setUp() {
        mocks = new ProductMocks();
    }

    @Test
    void shouldSortProductsSuccessfully() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);
        SortingCriteria criteria = mocks.createTestSortingCriteria();
        List<Product> sortedProducts = mocks.createTestProducts();
        SortProductsResponse expectedResponse = mocks.createMockResponse();

        given(productMapper.toDomain(request)).willReturn(criteria);
        given(productServicePort.sortProducts(criteria)).willReturn(sortedProducts);
        given(productMapper.toResponse(sortedProducts, criteria)).willReturn(expectedResponse);

        // When
        ResponseEntity<SortProductsResponse> result = productControllerAdapter.sortProducts(request);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(expectedResponse, result.getBody());

        // Verify interactions
        verify(productMapper, times(1)).toDomain(request);
        verify(productServicePort, times(1)).sortProducts(criteria);
        verify(productMapper, times(1)).toResponse(sortedProducts, criteria);
    }

    @Test
    void shouldSortProductsWithBalancedCriteria() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.5, 0.5);
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(0.5)
                .stockRatioWeight(0.5)
                .build();
        List<Product> sortedProducts = mocks.createTestProducts();
        SortProductsResponse expectedResponse = mocks.createMockResponse();

        given(productMapper.toDomain(request)).willReturn(criteria);
        given(productServicePort.sortProducts(criteria)).willReturn(sortedProducts);
        given(productMapper.toResponse(sortedProducts, criteria)).willReturn(expectedResponse);

        // When
        ResponseEntity<SortProductsResponse> result = productControllerAdapter.sortProducts(request);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(productServicePort, times(1)).sortProducts(criteria);
    }

    @Test
    void shouldSortProductsWithSalesOnlyWeight() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(1.0, 0.0);
        SortingCriteria criteria = SortingCriteria.builder()
                .salesWeight(1.0)
                .stockRatioWeight(0.0)
                .build();
        List<Product> sortedProducts = Collections.singletonList(mocks.createTestProduct());
        SortProductsResponse expectedResponse = mocks.createMockResponse();

        given(productMapper.toDomain(request)).willReturn(criteria);
        given(productServicePort.sortProducts(criteria)).willReturn(sortedProducts);
        given(productMapper.toResponse(sortedProducts, criteria)).willReturn(expectedResponse);

        // When
        ResponseEntity<SortProductsResponse> result = productControllerAdapter.sortProducts(request);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }


    @Test
    void shouldHandleIllegalArgumentExceptionFromService() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);
        SortingCriteria criteria = mocks.createTestSortingCriteria();

        given(productMapper.toDomain(request)).willReturn(criteria);
        willThrow(new IllegalArgumentException("Invalid sorting criteria"))
                .given(productServicePort).sortProducts(criteria);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productControllerAdapter.sortProducts(request));

        assertEquals("Invalid sorting criteria", exception.getMessage());
        verify(productMapper, times(1)).toDomain(request);
        verify(productServicePort, times(1)).sortProducts(criteria);
        verify(productMapper, never()).toResponse(any(), any());
    }

    @Test
    void shouldHandleNullPointerExceptionFromService() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);
        SortingCriteria criteria = mocks.createTestSortingCriteria();

        given(productMapper.toDomain(request)).willReturn(criteria);
        willThrow(new NullPointerException())
                .given(productServicePort).sortProducts(criteria);

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> productControllerAdapter.sortProducts(request));

        assertNotNull(exception);
        verify(productServicePort, times(1)).sortProducts(criteria);
    }

    @Test
    void shouldHandleRuntimeExceptionFromService() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);
        SortingCriteria criteria = mocks.createTestSortingCriteria();

        given(productMapper.toDomain(request)).willReturn(criteria);
        willThrow(new RuntimeException("Database connection failed"))
                .given(productServicePort).sortProducts(criteria);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productControllerAdapter.sortProducts(request));

        assertEquals("Database connection failed", exception.getMessage());
        verify(productServicePort, times(1)).sortProducts(criteria);
    }

    @Test
    void shouldHandleMapperException() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);

        willThrow(new IllegalArgumentException("Invalid request format"))
                .given(productMapper).toDomain(request);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productControllerAdapter.sortProducts(request));

        assertEquals("Invalid request format", exception.getMessage());
        verify(productMapper, times(1)).toDomain(request);
        verify(productServicePort, never()).sortProducts(any());
    }

    @Test
    void shouldPassCorrectCriteriaToService() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.8, 0.2);
        SortingCriteria expectedCriteria = SortingCriteria.builder()
                .salesWeight(0.8)
                .stockRatioWeight(0.2)
                .build();
        List<Product> sortedProducts = mocks.createTestProducts();
        SortProductsResponse expectedResponse = mocks.createMockResponse();

        given(productMapper.toDomain(request)).willReturn(expectedCriteria);
        given(productServicePort.sortProducts(expectedCriteria)).willReturn(sortedProducts);
        given(productMapper.toResponse(sortedProducts, expectedCriteria)).willReturn(expectedResponse);

        // When
        productControllerAdapter.sortProducts(request);

        // Then
        verify(productServicePort, times(1)).sortProducts(expectedCriteria);
        verify(productMapper, times(1)).toResponse(sortedProducts, expectedCriteria);
    }

    @Test
    void shouldReturnMappedResponseFromService() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);
        SortingCriteria criteria = mocks.createTestSortingCriteria();
        List<Product> sortedProducts = mocks.createTestProducts();
        SortProductsResponse expectedResponse = mocks.createMockResponse();

        given(productMapper.toDomain(request)).willReturn(criteria);
        given(productServicePort.sortProducts(criteria)).willReturn(sortedProducts);
        given(productMapper.toResponse(sortedProducts, criteria)).willReturn(expectedResponse);

        // When
        ResponseEntity<SortProductsResponse> result = productControllerAdapter.sortProducts(request);

        // Then
        assertSame(expectedResponse, result.getBody());
        verify(productMapper, times(1)).toResponse(sortedProducts, criteria);
    }

    @Test
    void shouldMaintainCorrectFlowOrder() {
        // Given
        SortProductsRequest request = mocks.createSortProductsRequest(0.7, 0.3);
        SortingCriteria criteria = mocks.createTestSortingCriteria();
        List<Product> sortedProducts = mocks.createTestProducts();
        SortProductsResponse expectedResponse = mocks.createMockResponse();

        given(productMapper.toDomain(request)).willReturn(criteria);
        given(productServicePort.sortProducts(criteria)).willReturn(sortedProducts);
        given(productMapper.toResponse(sortedProducts, criteria)).willReturn(expectedResponse);

        // When
        productControllerAdapter.sortProducts(request);

        // Then - Verify order of execution
        var inOrder = inOrder(productMapper, productServicePort);
        inOrder.verify(productMapper).toDomain(request);
        inOrder.verify(productServicePort).sortProducts(criteria);
        inOrder.verify(productMapper).toResponse(sortedProducts, criteria);
    }

}
