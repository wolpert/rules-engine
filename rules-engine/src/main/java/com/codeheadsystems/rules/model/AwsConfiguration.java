package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableAwsConfiguration.class)
@JsonDeserialize(builder = ImmutableAwsConfiguration.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface AwsConfiguration {

  static AwsConfiguration defaultConfiguration() {
    return ImmutableAwsConfiguration.builder().build();
  }

  @Value.Default
  @JsonProperty("region")
  default String region() {
    return "us-east-1";
  }

  @Value.Default
  @JsonProperty("s3RulePrefix")
  default String s3RulePrefix() {
    return "/rules";
  }

}
