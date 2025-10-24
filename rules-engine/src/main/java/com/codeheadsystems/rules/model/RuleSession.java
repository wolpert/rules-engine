package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

/**
 * The interface Tenant rule session.
 */
@Value.Immutable
public interface RuleSession {


  /**
   * Request rule execution request.
   *
   * @return the rule execution request
   */
  RuleExecutionRequest request();

  /**
   * Container tenant container.
   *
   * @return the tenant container
   */
  RuleExecutionContainer container();

  /**
   * Facts facts.
   *
   * @return the facts
   */
  Facts facts();
}
