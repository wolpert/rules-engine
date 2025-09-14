package com.codeheadsystems.api.basic.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Key.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableKey.class)
@JsonDeserialize(builder = ImmutableKey.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Key {

  /**
   * Uuid string.
   *
   * @return the string
   */
  String uuid();

  /**
   * Key string.
   *
   * @return the string
   */
  @JsonProperty("key")
  String key();

}
