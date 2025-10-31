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
@JsonSerialize(as = ImmutableRule.class)
@JsonDeserialize(builder = ImmutableRule.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Rule {

  /**
   * Identifier rule identifier.
   *
   * @return the rule identifier
   */
  @JsonProperty("identifier")
  RuleIdentifier identifier();

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
}