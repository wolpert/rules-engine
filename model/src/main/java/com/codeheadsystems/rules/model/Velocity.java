package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableVelocityDefinition.class)
@JsonDeserialize(builder = ImmutableVelocityDefinition.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Velocity {

  @JsonProperty("identifier")
  VelocityIdentifier identifier();

  @JsonProperty("variableName")
  VelocityVariableName variableName();

  @JsonProperty("amount")
  @Value.Auxiliary
  VelocityAmount amount();

}
