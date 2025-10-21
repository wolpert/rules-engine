package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.ExecutionEnvironment;
import com.codeheadsystems.rules.model.ImmutableRuleExecutionRequest;
import com.codeheadsystems.rules.model.RuleExecutionRequest;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.Version;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RuleExecutionRequestManager {

  private final GlobalRulesVersionManager globalRulesVersionManager;

  @Inject
  public RuleExecutionRequestManager(final GlobalRulesVersionManager globalRulesVersionManager) {
    this.globalRulesVersionManager = globalRulesVersionManager;
  }

  public RuleExecutionRequest forTenant(ExecutionEnvironment executionEnvironment, Tenant tenant) {
    return ImmutableRuleExecutionRequest.builder()
        .executionEnvironment(executionEnvironment)
        .tenant(tenant)
        .tenantRuleVersion(Version.of("1.0")) // Stubbed for now.
        .globalRuleVersion(globalRulesVersionManager.getActiveVersion())
        .build();
  }

}
