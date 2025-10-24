package com.codeheadsystems.rules.manager;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.converter.FactsConverter;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableTenant;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.module.AwsModule;
import com.codeheadsystems.rules.module.RuleEngineServerModule;
import com.codeheadsystems.server.module.DropWizardModule;
import dagger.Component;
import io.dropwizard.core.setup.Environment;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RuleExecutionManagerTest {

  private static final Tenant TENANT = ImmutableTenant.builder().name("tenant").build();
  private static final String EVENT_ID = "11-eventId";
  private static final String PAYLOAD = "{\"color\":\"blue\"}";

  @Mock private Environment environment;

  private RulesEngineConfiguration configuration;
  private RuleExecutionManager ruleExecutionManager;
  private FactsConverter factsConverter;

  @BeforeEach
  void setup() {
    FileAccessor fileAccessor = new FileAccessor() {
      @Override
      public List<String> listFiles(final String path) {
        return List.of("/drl/tenant/tenant.drl");
      }

      @Override
      public Optional<InputStream> getFile(final String path) {
        return Optional.of(getClass().getResourceAsStream(path));
      }
    };


    configuration = new RulesEngineConfiguration();
    TestComponent component = DaggerRuleExecutionManagerTest_TestComponent.builder()
        .dropWizardModule(new DropWizardModule(environment, configuration))
        .ruleEngineServerModule(new RuleEngineServerModule(fileAccessor))
        .build();
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