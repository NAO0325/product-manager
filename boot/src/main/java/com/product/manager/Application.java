package com.product.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.product.manager.application",
        "com.product.manager.boot",
        "com.product.manager.config",
        "com.product.manager.driven.repositories",
        "com.product.manager.driving.controllers"
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}