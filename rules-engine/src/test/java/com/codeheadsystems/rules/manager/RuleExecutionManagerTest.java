package com.codeheadsystems.rules.manager;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.converter.FactsConverter;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableTenant;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.module.AwsModule;
import com.codeheadsystems.rules.module.RuleEngineServerModule;
import com.codeheadsystems.server.module.DropWizardModule;
import dagger.Component;
import javax.inject.Singleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RuleExecutionManagerTest {

  private static final Tenant TENANT = ImmutableTenant.builder().name("tenant").build();
  private static final String EVENT_ID = "11-eventId";
  private static final String PAYLOAD = "{\"color\":\"blue\"}";

  private RuleExecutionManager ruleExecutionManager;
  private FactsConverter factsConverter;

  @BeforeEach
  void setup() {
    TestComponent component = DaggerRuleExecutionManagerTest_TestComponent.create();
    ruleExecutionManager = component.ruleExecutionManager();
    factsConverter = component.factsConverter();
  }

  @Test
  void testRuleExecutionManager() {
    Facts facts = factsConverter.convert(EVENT_ID, PAYLOAD);
    RuleExecutionResult result = ruleExecutionManager.executeRules(TENANT, facts);
    assertThat(result)
        .isNotNull()
        .hasEntrySatisfying("we found", v -> assertThat(v).isEqualTo("blue"));
  }

  @Component(modules = {AwsModule.class, DropWizardModule.class, RuleEngineServerModule.class,})
  @Singleton
  interface TestComponent {

    RuleExecutionManager ruleExecutionManager();

    FactsConverter factsConverter();

  }

}