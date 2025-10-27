package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Tenant.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableTenant.class)
@JsonDeserialize(builder = ImmutableTenant.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Tenant {

  /**
   * Of tenant.
   *
   * @param value the value
   * @return the tenant
   */
  static Tenant of(final String value) {
    return ImmutableTenant.builder().value(value).build();
  }

  /**
   * Name string.
   *
   * @return the string
   */
  @JsonProperty("value")
  String value();
}
