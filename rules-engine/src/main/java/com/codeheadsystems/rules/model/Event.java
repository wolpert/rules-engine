package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Event type.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableEvent.class)
@JsonDeserialize(builder = ImmutableEvent.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Event {

  /**
   * The constant DEFAULT.
   */
  Event DEFAULT = Event.of("DEFAULT");

  /**
   * Of event type.
   *
   * @param eventType the event type
   * @return the event type
   */
  static Event of(String eventType) {
    return ImmutableEvent.builder().value(eventType).build();
  }

  /**
   * Name string.
   *
   * @return the string
   */
  @JsonProperty("value")
  String value();

}
