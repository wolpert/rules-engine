package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

@Value.Immutable
public interface TenantRuleSession {
  Tenant tenant();
  TenantContainer container();
  Facts facts();
}
