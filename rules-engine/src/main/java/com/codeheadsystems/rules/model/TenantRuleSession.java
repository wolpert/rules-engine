package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

/**
 * The interface Tenant rule session.
 */
@Value.Immutable
public interface TenantRuleSession {
  /**
   * Tenant tenant.
   *
   * @return the tenant
   */
  Tenant tenant();

  /**
   * Container tenant container.
   *
   * @return the tenant container
   */
  TenantContainer container();

  /**
   * Facts facts.
   *
   * @return the facts
   */
  Facts facts();
}
