package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface VelocityAmount.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableVelocityAmount.class)
@JsonDeserialize(builder = ImmutableVelocityAmount.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VelocityAmount {

  /**
   * Of VelocityAmount.
   *
   * @param value the value
   * @return the tenant
   */
  static VelocityAmount of(final String value) {
    return ImmutableVelocityAmount.builder().value(value).build();
  }

  /**
   * Name string.
   *
   * @return the string
   */
  @JsonProperty("value")
  String value();
}
