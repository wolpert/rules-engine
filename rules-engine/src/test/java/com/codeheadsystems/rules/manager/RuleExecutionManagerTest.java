package com.codeheadsystems.rules.manager;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RuleExecutionManagerTest {

  @Test
  void testRuleExecutionManager() {
    RuleExecutionManager ruleExecutionManager = new RuleExecutionManager();
    ruleExecutionManager.sampleRun();
  }

}