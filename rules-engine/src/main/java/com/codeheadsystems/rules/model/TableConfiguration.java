package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The interface Table configuration.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableTableConfiguration.class)
@JsonDeserialize(builder = ImmutableTableConfiguration.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface TableConfiguration {

  /**
   * Table name string.
   *
   * @return the string
   */
  @Value.Default
  @JsonProperty("tableName")
  default String tableName() {
    return "rules_data_store";
  }

  /**
   * Hash key name string.
   *
   * @return the string
   */
  @Value.Default
  @JsonProperty("hashKeyName")
  default String hashKeyName() {
    return "hash_key";
  }

  /**
   * Sort key name string.
   *
   * @return the string
   */
  @Value.Default
  @JsonProperty("sortKeyName")
  default String sortKeyName() {
    return "sort_key";
  }

  /**
   * Type col name string.
   *
   * @return the string
   */
  @Value.Default
  @JsonProperty("typeColName")
  default String typeColName() {
    return "type";
  }

  /**
   * Ttl col name string.
   *
   * @return the string
   */
  @Value.Default
  @JsonProperty("ttlColName")
  default String ttlColName() {
    return "ttl";
  }

}
