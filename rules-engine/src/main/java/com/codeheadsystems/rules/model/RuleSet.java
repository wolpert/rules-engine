package com.codeheadsystems.rules.model;

import org.immutables.value.Value;
import org.kie.api.runtime.KieContainer;

/**
 * The interface Tenant container.
 */
@Value.Immutable
public interface RuleSet {

  /**
   * Rule execution request rule execution request.
   *
   * @return the rule execution request
   */
  RuleSetIdentifier ruleExecutionRequest();

  /**
   * Kie container kie container.
   *
   * @return the kie container
   */
  KieContainer kieContainer();

}
