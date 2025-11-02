package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Rule.
 * This represents a single rule 'file' within the rule engine for a specific tenant. It is not event specific
 * by definition. Rules can have their own metrics, run in shadow mode, etc. The are applied to a rule-set.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableVelocityIdentifier.class)
@JsonDeserialize(builder = ImmutableVelocityIdentifier.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VelocityIdentifier {

  /**
   * Tenant tenant.
   *
   * @return the tenant
   */
  @JsonProperty("tenant")
  Tenant tenant();

  /**
   * Id string.
   *
   * @return the string
   */
  @JsonProperty("id")
  String id();

  /**
   * Version version.
   *
   * @return the version
   */
  @JsonProperty("version")
  Version version();

}