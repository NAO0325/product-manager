package com.product.manager.driving.controllers.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.product.manager.driving.controllers.models.Product;
import com.product.manager.driving.controllers.models.SortingCriteria;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SortProductsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-04T09:53:31.625421774+02:00[Europe/Madrid]")
public class SortProductsResponse {

  @Valid
  private List<@Valid Product> products = new ArrayList<>();

  private SortingCriteria appliedCriteria;

  private Integer totalProducts;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  public SortProductsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SortProductsResponse(List<@Valid Product> products, SortingCriteria appliedCriteria, Integer totalProducts, OffsetDateTime timestamp) {
    this.products = products;
    this.appliedCriteria = appliedCriteria;
    this.totalProducts = totalProducts;
    this.timestamp = timestamp;
  }

  public SortProductsResponse products(List<@Valid Product> products) {
    this.products = products;
    return this;
  }

  public SortProductsResponse addProductsItem(Product productsItem) {
    if (this.products == null) {
      this.products = new ArrayList<>();
    }
    this.products.add(productsItem);
    return this;
  }

  /**
   * Products sorted by calculated score (highest to lowest)
   * @return products
  */
  @NotNull @Valid 
  @Schema(name = "products", description = "Products sorted by calculated score (highest to lowest)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("products")
  public List<@Valid Product> getProducts() {
    return products;
  }

  public void setProducts(List<@Valid Product> products) {
    this.products = products;
  }

  public SortProductsResponse appliedCriteria(SortingCriteria appliedCriteria) {
    this.appliedCriteria = appliedCriteria;
    return this;
  }

  /**
   * Get appliedCriteria
   * @return appliedCriteria
  */
  @NotNull @Valid 
  @Schema(name = "appliedCriteria", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("appliedCriteria")
  public SortingCriteria getAppliedCriteria() {
    return appliedCriteria;
  }

  public void setAppliedCriteria(SortingCriteria appliedCriteria) {
    this.appliedCriteria = appliedCriteria;
  }

  public SortProductsResponse totalProducts(Integer totalProducts) {
    this.totalProducts = totalProducts;
    return this;
  }

  /**
   * Total number of products returned
   * @return totalProducts
  */
  @NotNull 
  @Schema(name = "totalProducts", example = "6", description = "Total number of products returned", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalProducts")
  public Integer getTotalProducts() {
    return totalProducts;
  }

  public void setTotalProducts(Integer totalProducts) {
    this.totalProducts = totalProducts;
  }

  public SortProductsResponse timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Timestamp when the sorting was performed
   * @return timestamp
  */
  @NotNull @Valid 
  @Schema(name = "timestamp", example = "2024-06-03T14:30Z", description = "Timestamp when the sorting was performed", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("timestamp")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SortProductsResponse sortProductsResponse = (SortProductsResponse) o;
    return Objects.equals(this.products, sortProductsResponse.products) &&
        Objects.equals(this.appliedCriteria, sortProductsResponse.appliedCriteria) &&
        Objects.equals(this.totalProducts, sortProductsResponse.totalProducts) &&
        Objects.equals(this.timestamp, sortProductsResponse.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(products, appliedCriteria, totalProducts, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SortProductsResponse {\n");
    sb.append("    products: ").append(toIndentedString(products)).append("\n");
    sb.append("    appliedCriteria: ").append(toIndentedString(appliedCriteria)).append("\n");
    sb.append("    totalProducts: ").append(toIndentedString(totalProducts)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

