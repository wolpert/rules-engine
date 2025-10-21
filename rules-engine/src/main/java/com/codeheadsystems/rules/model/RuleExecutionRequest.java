package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

@Value.Immutable
public interface RuleExecutionRequest {

  ExecutionEnvironment executionEnvironment();
  Tenant tenant();
  Version tenantRuleVersion();
  Version globalRuleVersion();


}
