package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

/**
 * The interface Tenant.
 */
@Value.Immutable
public interface Tenant {

  /**
   * Name string.
   *
   * @return the string
   */
  String name();
}
