package com.codeheadsystems.rules.model;

import org.immutables.value.Value;
import org.kie.api.runtime.KieContainer;

/**
 * The interface Tenant container.
 */
@Value.Immutable
public interface TenantContainer {

  /**
   * Tenant tenant.
   *
   * @return the tenant
   */
  Tenant tenant();

  /**
   * Kie container kie container.
   *
   * @return the kie container
   */
  KieContainer kieContainer();

}
