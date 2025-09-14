package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

@Value.Immutable
public interface TableConfiguration {

  @Value.Default
  default String tableName() {
    return "rules_table";
  }

  @Value.Default
  default String hashKeyName() {
    return "hash_key";
  }

  @Value.Default
  default String sortKeyName() {
    return "sort_key";
  }

  @Value.Default
  default String typeColName() {
    return "type";
  }

  @Value.Default
  default String ttlColName() {
    return "ttl";
  }

}
