package com.product.manager.driving.controllers.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Product
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-04T09:53:31.625421774+02:00[Europe/Madrid]")
public class Product {

  private Long id;

  private String name;

  private Integer salesUnits;

  @Valid
  private Map<String, Integer> stockBySizes = new HashMap<>();

  private Double stockRatio;

  public Product() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Product(Long id, String name, Integer salesUnits, Map<String, Integer> stockBySizes, Double stockRatio) {
    this.id = id;
    this.name = name;
    this.salesUnits = salesUnits;
    this.stockBySizes = stockBySizes;
    this.stockRatio = stockRatio;
  }

  public Product id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Product identifier
   * @return id
  */
  @NotNull 
  @Schema(name = "id", example = "1", description = "Product identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Product name
   * @return name
  */
  @NotNull 
  @Schema(name = "name", example = "V-NECH BASIC SHIRT", description = "Product name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product salesUnits(Integer salesUnits) {
    this.salesUnits = salesUnits;
    return this;
  }

  /**
   * Number of units sold
   * minimum: 0
   * @return salesUnits
  */
  @NotNull @Min(0) 
  @Schema(name = "salesUnits", example = "100", description = "Number of units sold", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("salesUnits")
  public Integer getSalesUnits() {
    return salesUnits;
  }

  public void setSalesUnits(Integer salesUnits) {
    this.salesUnits = salesUnits;
  }

  public Product stockBySizes(Map<String, Integer> stockBySizes) {
    this.stockBySizes = stockBySizes;
    return this;
  }

  public Product putStockBySizesItem(String key, Integer stockBySizesItem) {
    if (this.stockBySizes == null) {
      this.stockBySizes = new HashMap<>();
    }
    this.stockBySizes.put(key, stockBySizesItem);
    return this;
  }

  /**
   * Stock quantity by size
   * @return stockBySizes
  */
  @NotNull 
  @Schema(name = "stockBySizes", example = "{\"S\":4,\"M\":9,\"L\":0}", description = "Stock quantity by size", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stockBySizes")
  public Map<String, Integer> getStockBySizes() {
    return stockBySizes;
  }

  public void setStockBySizes(Map<String, Integer> stockBySizes) {
    this.stockBySizes = stockBySizes;
  }

  public Product stockRatio(Double stockRatio) {
    this.stockRatio = stockRatio;
    return this;
  }

  /**
   * Ratio of sizes with available stock (0.0 - 1.0)
   * minimum: 0.0
   * maximum: 1.0
   * @return stockRatio
  */
  @NotNull @DecimalMin("0.0") @DecimalMax("1.0") 
  @Schema(name = "stockRatio", example = "0.67", description = "Ratio of sizes with available stock (0.0 - 1.0)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stockRatio")
  public Double getStockRatio() {
    return stockRatio;
  }

  public void setStockRatio(Double stockRatio) {
    this.stockRatio = stockRatio;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.id, product.id) &&
        Objects.equals(this.name, product.name) &&
        Objects.equals(this.salesUnits, product.salesUnits) &&
        Objects.equals(this.stockBySizes, product.stockBySizes) &&
        Objects.equals(this.stockRatio, product.stockRatio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, salesUnits, stockBySizes, stockRatio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    salesUnits: ").append(toIndentedString(salesUnits)).append("\n");
    sb.append("    stockBySizes: ").append(toIndentedString(stockBySizes)).append("\n");
    sb.append("    stockRatio: ").append(toIndentedString(stockRatio)).append("\n");
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

