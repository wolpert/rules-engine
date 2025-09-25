package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

/**
 * The interface Table configuration.
 */
@Value.Immutable
public interface TableConfiguration {

  /**
   * Table name string.
   *
   * @return the string
   */
  @Value.Default
  default String tableName() {
    return "rules_data_store";
  }

  /**
   * Hash key name string.
   *
   * @return the string
   */
  @Value.Default
  default String hashKeyName() {
    return "hash_key";
  }

  /**
   * Sort key name string.
   *
   * @return the string
   */
  @Value.Default
  default String sortKeyName() {
    return "sort_key";
  }

  /**
   * Type col name string.
   *
   * @return the string
   */
  @Value.Default
  default String typeColName() {
    return "type";
  }

  /**
   * Ttl col name string.
   *
   * @return the string
   */
  @Value.Default
  default String ttlColName() {
    return "ttl";
  }

}
