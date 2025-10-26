package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Event;
import com.codeheadsystems.rules.model.ExecutionEnvironment;
import com.codeheadsystems.rules.model.ImmutableRuleSetIdentifier;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.Version;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Rule execution request manager.
 */
@Singleton
public class RuleSetIdentifierManager {

  private final RuleVersionManager ruleVersionManager;
  private final ExecutionEnvironment executionEnvironment;

  /**
   * Instantiates a new Rule execution request manager.
   *
   * @param ruleVersionManager   the rules version manager
   * @param executionEnvironment the execution environment
   */
  @Inject
  public RuleSetIdentifierManager(final RuleVersionManager ruleVersionManager,
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
  public RuleSetIdentifier forTenant(Tenant tenant) {
    return ImmutableRuleSetIdentifier.builder()
        .executionEnvironment(executionEnvironment)
        .tenant(tenant)
        .event(Event.DEFAULT)
        .eventVersion(Version.of("1.0"))
        .build();
  }

}
