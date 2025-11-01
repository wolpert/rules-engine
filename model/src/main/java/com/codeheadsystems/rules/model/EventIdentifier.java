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
@JsonSerialize(as = ImmutableEventIdentifier.class)
@JsonDeserialize(builder = ImmutableEventIdentifier.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface EventIdentifier {

  /**
   * Of event identifier.
   *
   * @param id the id
   * @return the event identifier
   */
  static EventIdentifier of(final String id) {
    return ImmutableEventIdentifier.builder()
        .value(id)
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
