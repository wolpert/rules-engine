package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Version.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableVersion.class)
@JsonDeserialize(builder = ImmutableVersion.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Version {

  /**
   * Of version.
   *
   * @param version the version
   * @return the version
   */
  static Version of(final String version) {
    return ImmutableVersion.builder()
        .value(version)
        .build();
  }

  /**
   * Value string.
   *
   * @return the string
   */
  @JsonProperty("value")
  String value();

}
