package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Optional;
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
  Optional<EventType> eventType();

  /**
   * Global rule version version.
   *
   * @return the version
   */
  @JsonProperty("globalRuleVersion")
  Version globalRuleVersion();

  /**
   * Tenant rule version version.
   *
   * @return the version
   */
  @JsonProperty("tenantRuleVersion")
  Version tenantRuleVersion();

  /**
   * Event version optional.
   *
   * @return the optional
   */
  @JsonProperty("eventVersion")
  Optional<Version> eventVersion();

}
