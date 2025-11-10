package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableVelocitySet.class)
@JsonDeserialize(builder = ImmutableVelocitySet.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VelocitySet {

  @JsonProperty("definitions")
  Set<VelocityDefinition> definitions();

}
