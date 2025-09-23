package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

/**
 * The interface Tenant.
 */
@Value.Immutable
public interface Tenant {

  /**
   * Of tenant.
   *
   * @param name the name
   * @return the tenant
   */
  static Tenant of(final String name) {
    return ImmutableTenant.builder().name(name).build();
  }

  /**
   * Name string.
   *
   * @return the string
   */
  String name();
}
