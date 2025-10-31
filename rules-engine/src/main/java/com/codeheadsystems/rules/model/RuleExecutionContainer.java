package com.codeheadsystems.rules.model;

import org.immutables.value.Value;
import org.kie.api.runtime.KieContainer;

/**
 * The interface Rule execution container.
 */
@Value.Immutable
public interface RuleExecutionContainer {

  /**
   * Rule set rule set.
   *
   * @return the rule set
   */
  RuleSet ruleSet();

  /**
   * Kie container kie container.
   *
   * @return the kie container
   */
  KieContainer kieContainer();

}
