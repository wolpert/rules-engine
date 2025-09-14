package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

@Value.Immutable
public interface TableDetails {

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

}
