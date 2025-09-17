package com.codeheadsystems.rules.converter;

import com.codeheadsystems.api.rules.engine.v1.ExecutionResult;
import com.codeheadsystems.api.rules.engine.v1.ImmutableExecutionResult;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Execution result converter.
 */
@Singleton
public class ExecutionResultConverter {

  /**
   * Instantiates a new Execution result converter.
   */
  @Inject
  public ExecutionResultConverter() {
  }

  /**
   * Convert execution result.
   *
   * @param ruleExecutionResult the rule execution result
   * @return the execution result
   */
  public ExecutionResult convert(RuleExecutionResult ruleExecutionResult) {
    return ImmutableExecutionResult.builder()
        .putAllRuleExecutionResponse(ruleExecutionResult)
        .build();
  }

}
