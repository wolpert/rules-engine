package com.codeheadsystems.rules.converter;

import com.codeheadsystems.api.rules.engine.v1.ExecutionResult;
import com.codeheadsystems.api.rules.engine.v1.ImmutableExecutionResult;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExecutionResultConverter {

  @Inject
  public ExecutionResultConverter() {
  }

  public ExecutionResult convert(RuleExecutionResult ruleExecutionResult) {
    return ImmutableExecutionResult.builder()
        .putAllRuleExecutionResponse(ruleExecutionResult)
        .build();
  }

}
