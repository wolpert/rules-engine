package com.codeheadsystems.rules.converter;

import com.codeheadsystems.api.rules.engine.v1.ExecutionResult;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExecutionResultConverterTest {

  private ExecutionResultConverter converter;

  @BeforeEach
  void setUp() {
    converter = new ExecutionResultConverter();
  }

  @Test
  void convert_shouldReturnExecutionResult() {
    RuleExecutionResult ruleExecutionResult = mock(RuleExecutionResult.class);

    ExecutionResult result = converter.convert(ruleExecutionResult);

    assertThat(result).isNotNull();
    // Optionally, add more assertions based on ExecutionResult's properties
  }
}
