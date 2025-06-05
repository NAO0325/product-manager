package com.product.manager.driving.controllers.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.product.manager.driving.controllers.models.SortingCriteria;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SortProductsRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-04T09:53:31.625421774+02:00[Europe/Madrid]")
public class SortProductsRequest {

  private SortingCriteria criteria;

  public SortProductsRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SortProductsRequest(SortingCriteria criteria) {
    this.criteria = criteria;
  }

  public SortProductsRequest criteria(SortingCriteria criteria) {
    this.criteria = criteria;
    return this;
  }

  /**
   * Get criteria
   * @return criteria
  */
  @NotNull @Valid 
  @Schema(name = "criteria", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("criteria")
  public SortingCriteria getCriteria() {
    return criteria;
  }

  public void setCriteria(SortingCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SortProductsRequest sortProductsRequest = (SortProductsRequest) o;
    return Objects.equals(this.criteria, sortProductsRequest.criteria);
  }

  @Override
  public int hashCode() {
    return Objects.hash(criteria);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SortProductsRequest {\n");
    sb.append("    criteria: ").append(toIndentedString(criteria)).append("\n");
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

