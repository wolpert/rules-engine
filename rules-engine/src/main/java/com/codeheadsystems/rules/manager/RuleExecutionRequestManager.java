package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.ExecutionEnvironment;
import com.codeheadsystems.rules.model.ImmutableRuleExecutionRequest;
import com.codeheadsystems.rules.model.RuleExecutionRequest;
import com.codeheadsystems.rules.model.Tenant;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Rule execution request manager.
 */
@Singleton
public class RuleExecutionRequestManager {

  private final RulesVersionManager rulesVersionManager;
  private final ExecutionEnvironment executionEnvironment;

  /**
   * Instantiates a new Rule execution request manager.
   *
   * @param rulesVersionManager  the rules version manager
   * @param executionEnvironment the execution environment
   */
  @Inject
  public RuleExecutionRequestManager(final RulesVersionManager rulesVersionManager,
                                     final ExecutionEnvironment executionEnvironment) {
    this.rulesVersionManager = rulesVersionManager;
    this.executionEnvironment = executionEnvironment;
  }

  /**
   * For tenant rule execution request.
   *
   * @param tenant the tenant
   * @return the rule execution request
   */
  public RuleExecutionRequest forTenant(Tenant tenant) {
    return ImmutableRuleExecutionRequest.builder()
        .executionEnvironment(executionEnvironment)
        .tenant(tenant)
        .tenantRuleVersion(rulesVersionManager.getActiveTenantVersion(tenant)) // Stubbed for now.
        .globalRuleVersion(rulesVersionManager.getActiveGlobalVersion())
        .build();
  }

}
