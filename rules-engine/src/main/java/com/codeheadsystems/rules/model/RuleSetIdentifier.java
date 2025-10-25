package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Rule execution request.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableRuleSetIdentifier.class)
@JsonDeserialize(builder = ImmutableRuleSetIdentifier.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RuleSetIdentifier {

  /**
   * Execution environment execution environment.
   *
   * @return the execution environment
   */
  @JsonProperty("executionEnvironment")
  ExecutionEnvironment executionEnvironment();

  /**
   * Tenant tenant.
   *
   * @return the tenant
   */
  @JsonProperty("tenant")
  Tenant tenant();

  /**
   * Event type optional.
   *
   * @return the optional
   */
  @JsonProperty("eventType")
  EventType eventType();

  /**
   * Event version optional.
   *
   * @return the optional
   */
  @JsonProperty("eventVersion")
  Version eventVersion();

}
