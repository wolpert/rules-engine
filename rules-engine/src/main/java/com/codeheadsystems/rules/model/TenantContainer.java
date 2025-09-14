package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

@Value.Immutable
public interface TenantContainer {

  Tenant tenant();

}
