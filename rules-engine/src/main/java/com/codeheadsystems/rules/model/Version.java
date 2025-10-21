package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableVersion.class)
@JsonDeserialize(builder = ImmutableVersion.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Version {

  static Version of(final String version) {
    return ImmutableVersion.builder()
        .value(version)
        .build();
  }

  @JsonProperty("value")
  String value();

}
