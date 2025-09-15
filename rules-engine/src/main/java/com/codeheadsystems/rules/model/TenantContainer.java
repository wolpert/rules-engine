package com.codeheadsystems.rules.model;

import org.immutables.value.Value;
import org.kie.api.runtime.KieContainer;

@Value.Immutable
public interface TenantContainer {

  Tenant tenant();

  KieContainer kieContainer();

}
