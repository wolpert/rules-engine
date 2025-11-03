package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * The interface Rule.
 * This represents a single rule 'file' within the rule engine for a specific tenant. It is not event specific
 * by definition. Rules can have their own metrics, run in shadow mode, etc. The are applied to a rule-set.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableVelocityDefinition.class)
@JsonDeserialize(builder = ImmutableVelocityDefinition.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VelocityDefinition {

  /**
   * Identifier rule identifier.
   *
   * @return the rule identifier
   */
  @JsonProperty("identifier")
  VelocityIdentifier identifier();

  /**
   * Name string.
   *
   * @return the string
   */
  @JsonProperty("name")
  @Value.Auxiliary
  String name();

  /**
   * Description optional.
   *
   * @return the optional
   */
  @JsonProperty("description")
  @Value.Auxiliary
  Optional<String> description();

  /**
   * Window for the velocity event.
   *
   * @return the window
   */
  @JsonProperty("window")
  @Value.Auxiliary
  Window window();

  /**
   * The name to give the velocity identifier. (Like credit card number)
   *
   * @return the string
   */
  @JsonProperty("varName")
  @Value.Auxiliary
  String varName();

  /**
   * Path to the unique identifier in the JSON you want to count within the window.
   *
   * @return the string
   */
  @JsonProperty("varPath")
  @Value.Auxiliary
  String varPath();

  /**
   * Path to the unique value you want to aggregate per 'var' within the window. If
   * not set, each 'var' will be counted as 1 per event.
   *
   * @return the optional
   */
  @JsonProperty("valPath")
  @Value.Auxiliary
  Optional<String> valPath();

  /**
   * Type of aggregation. Note you cannot change this when you create a velocity as
   * it's not backwards compatible.
   *
   * @return the type
   */
  @JsonProperty("type")
  @Value.Auxiliary
  default VelocityValueType type() {
    return VelocityValueType.INTEGER;
  }

}