package com.product.manager.config;

import com.product.manager.domain.services.ProductSortingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ProductSortingService productSortingService() {
        return new ProductSortingService();
    }

}
