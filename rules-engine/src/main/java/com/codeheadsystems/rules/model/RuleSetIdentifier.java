package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

/**
 * The interface Rule execution request.
 */
@Value.Immutable
public interface RuleSetIdentifier {

  /**
   * Execution environment execution environment.
   *
   * @return the execution environment
   */
  ExecutionEnvironment executionEnvironment();

  /**
   * Tenant tenant.
   *
   * @return the tenant
   */
  Tenant tenant();

  /**
   * Tenant rule version version.
   *
   * @return the version
   */
  Version tenantRuleVersion();

  /**
   * Global rule version version.
   *
   * @return the version
   */
  Version globalRuleVersion();


}
