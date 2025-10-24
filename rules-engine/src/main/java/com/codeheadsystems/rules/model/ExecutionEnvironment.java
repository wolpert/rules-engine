package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The execution environment is taken from the stage set in the configuration. For this
 * project, the server-base top-level configuration defines a stage, which we use for
 * this value.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableExecutionEnvironment.class)
@JsonDeserialize(builder = ImmutableExecutionEnvironment.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ExecutionEnvironment {

  /**
   * Value string.
   *
   * @return the string
   */
  @JsonProperty("value")
  String value();

}
