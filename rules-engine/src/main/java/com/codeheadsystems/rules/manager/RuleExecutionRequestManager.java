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

  private final RuleVersionManager ruleVersionManager;
  private final ExecutionEnvironment executionEnvironment;

  /**
   * Instantiates a new Rule execution request manager.
   *
   * @param ruleVersionManager   the rules version manager
   * @param executionEnvironment the execution environment
   */
  @Inject
  public RuleExecutionRequestManager(final RuleVersionManager ruleVersionManager,
                                     final ExecutionEnvironment executionEnvironment) {
    this.ruleVersionManager = ruleVersionManager;
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
        .tenantRuleVersion(ruleVersionManager.getActiveTenantVersion(tenant)) // Stubbed for now.
        .globalRuleVersion(ruleVersionManager.getActiveGlobalVersion())
        .build();
  }

}
