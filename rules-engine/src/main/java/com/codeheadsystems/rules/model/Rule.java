package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * The interface Rule.
 * This represents a single rule 'file' within the rule engine for a specific tenant. It is not event specific
 * by definition. Rules can have their own metrics, run in shadow mode, etc. The are applied to a rule-set.
 */
@Value.Immutable
public interface Rule {

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