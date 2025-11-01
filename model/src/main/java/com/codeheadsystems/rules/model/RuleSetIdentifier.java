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
   * Of rule set identifier.
   *
   * @param tenant    the tenant
   * @param eventType the event
   * @param version   the version
   * @return the rule set identifier
   */
  static RuleSetIdentifier of(final Tenant tenant,
                              final EventType eventType,
                              final Version version) {
    return ImmutableRuleSetIdentifier.builder()
        .tenant(tenant)
        .eventType(eventType)
        .version(version)
        .build();
  }

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
   * Version version.
   *
   * @return the version
   */
  @JsonProperty("version")
  Version version();

}
