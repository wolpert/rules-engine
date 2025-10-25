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
@JsonSerialize(as = ImmutableEventType.class)
@JsonDeserialize(builder = ImmutableEventType.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface EventType {

  /**
   * The constant DEFAULT.
   */
  EventType DEFAULT = EventType.of("DEFAULT");

  /**
   * Of event type.
   *
   * @param eventType the event type
   * @return the event type
   */
  static EventType of(String eventType) {
    return ImmutableEventType.builder().value(eventType).build();
  }

  /**
   * Name string.
   *
   * @return the string
   */
  @JsonProperty("value")
  String value();

}
