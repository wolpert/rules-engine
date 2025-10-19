package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Aws configuration.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableAwsConfiguration.class)
@JsonDeserialize(builder = ImmutableAwsConfiguration.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface AwsConfiguration {

  /**
   * Default configuration aws configuration.
   *
   * @return the aws configuration
   */
  static AwsConfiguration defaultConfiguration() {
    return ImmutableAwsConfiguration.builder().build();
  }

  /**
   * Region string.
   *
   * @return the string
   */
  @Value.Default
  @JsonProperty("region")
  default String region() {
    return "us-east-1";
  }

  /**
   * Rules s 3 bucket string.
   *
   * @return the string
   */
  @Value.Default
  @JsonProperty("rulesS3Bucket")
  default String rulesS3Bucket() {
    return "codehead-rules-bucket";
  }

}
