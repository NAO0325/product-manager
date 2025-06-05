package com.product.manager.driving.controllers.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
/**
 * Map of criteria names to their weights. Weights should be non-negative numbers. The system will normalize the weights internally. 
 */

@Schema(name = "SortingCriteria_weights", description = "Map of criteria names to their weights. Weights should be non-negative numbers. The system will normalize the weights internally. ")
@JsonTypeName("SortingCriteria_weights")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-04T09:53:31.625421774+02:00[Europe/Madrid]")
public class SortingCriteriaWeights {

  private Double salesUnits;

  private Double stockRatio;

  public SortingCriteriaWeights salesUnits(Double salesUnits) {
    this.salesUnits = salesUnits;
    return this;
  }

  /**
   * Weight for sales units criteria
   * minimum: 0.0
   * maximum: 1.0
   * @return salesUnits
  */
  @DecimalMin("0.0") @DecimalMax("1.0") 
  @Schema(name = "sales_units", example = "0.7", description = "Weight for sales units criteria", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sales_units")
  public Double getSalesUnits() {
    return salesUnits;
  }

  public void setSalesUnits(Double salesUnits) {
    this.salesUnits = salesUnits;
  }

  public SortingCriteriaWeights stockRatio(Double stockRatio) {
    this.stockRatio = stockRatio;
    return this;
  }

  /**
   * Weight for stock ratio criteria
   * minimum: 0.0
   * maximum: 1.0
   * @return stockRatio
  */
  @DecimalMin("0.0") @DecimalMax("1.0") 
  @Schema(name = "stock_ratio", example = "0.3", description = "Weight for stock ratio criteria", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stock_ratio")
  public Double getStockRatio() {
    return stockRatio;
  }

  public void setStockRatio(Double stockRatio) {
    this.stockRatio = stockRatio;
  }
    /**
    * A container for additional, undeclared properties.
    * This is a holder for any undeclared properties as specified with
    * the 'additionalProperties' keyword in the OAS document.
    */
    private Map<String, Double> additionalProperties;

    /**
    * Set the additional (undeclared) property with the specified name and value.
    * If the property does not already exist, create it otherwise replace it.
    */
    @JsonAnySetter
    public SortingCriteriaWeights putAdditionalProperty(String key, Double value) {
        if (this.additionalProperties == null) {
            this.additionalProperties = new HashMap<String, Double>();
        }
        this.additionalProperties.put(key, value);
        return this;
    }

    /**
    * Return the additional (undeclared) property.
    */
    @JsonAnyGetter
    public Map<String, Double> getAdditionalProperties() {
        return additionalProperties;
    }

    /**
    * Return the additional (undeclared) property with the specified name.
    */
    public Double getAdditionalProperty(String key) {
        if (this.additionalProperties == null) {
            return null;
        }
        return this.additionalProperties.get(key);
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SortingCriteriaWeights sortingCriteriaWeights = (SortingCriteriaWeights) o;
    return Objects.equals(this.salesUnits, sortingCriteriaWeights.salesUnits) &&
        Objects.equals(this.stockRatio, sortingCriteriaWeights.stockRatio) &&
    Objects.equals(this.additionalProperties, sortingCriteriaWeights.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(salesUnits, stockRatio, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SortingCriteriaWeights {\n");
    sb.append("    salesUnits: ").append(toIndentedString(salesUnits)).append("\n");
    sb.append("    stockRatio: ").append(toIndentedString(stockRatio)).append("\n");
    
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
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

