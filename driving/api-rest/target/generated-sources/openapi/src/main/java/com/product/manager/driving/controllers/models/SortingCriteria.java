package com.product.manager.driving.controllers.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.product.manager.driving.controllers.models.SortingCriteriaWeights;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SortingCriteria
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-04T09:53:31.625421774+02:00[Europe/Madrid]")
public class SortingCriteria {

  private SortingCriteriaWeights weights;

  public SortingCriteria() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SortingCriteria(SortingCriteriaWeights weights) {
    this.weights = weights;
  }

  public SortingCriteria weights(SortingCriteriaWeights weights) {
    this.weights = weights;
    return this;
  }

  /**
   * Get weights
   * @return weights
  */
  @NotNull @Valid 
  @Schema(name = "weights", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("weights")
  public SortingCriteriaWeights getWeights() {
    return weights;
  }

  public void setWeights(SortingCriteriaWeights weights) {
    this.weights = weights;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SortingCriteria sortingCriteria = (SortingCriteria) o;
    return Objects.equals(this.weights, sortingCriteria.weights);
  }

  @Override
  public int hashCode() {
    return Objects.hash(weights);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SortingCriteria {\n");
    sb.append("    weights: ").append(toIndentedString(weights)).append("\n");
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

