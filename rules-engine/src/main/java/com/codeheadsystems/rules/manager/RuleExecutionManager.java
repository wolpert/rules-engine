package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.ImmutableTenant;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.Tenant;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RuleExecutionManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionManager.class);

  @Inject
  public RuleExecutionManager() {
    LOGGER.info("RuleExecutionManager()");
  }

  private KieContainer getKieContainer(String drlFile) {
    KieServices kieServices = KieServices.Factory.get();
    KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    List<String> rules = List.of(drlFile);
    for (String rule : rules) {
      kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
    }
    KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
    kieBuilder.buildAll();
    if (kieBuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
      LOGGER.error("Errors: {}", kieBuilder.getResults().toString());
      throw new IllegalStateException("### errors ###");
    }
    ReleaseId releaseId = kieBuilder.getKieModule().getReleaseId();
    KieContainer kieContainer = kieServices.newKieContainer(releaseId);
    return kieContainer;
  }

  public void executeRules(KieContainer kieContainer, Tenant tenant) {
    KieServices kieServices = KieServices.Factory.get();
    List cmds = List.of(
        kieServices.getCommands().newSetGlobal("rer", new RuleExecutionResult(), true),
        kieServices.getCommands().newInsert(tenant),
        kieServices.getCommands().newFireAllRules()
    );
    StatelessKieSession session = kieContainer.newStatelessKieSession();
    ExecutionResults results = session.execute(kieServices.getCommands().newBatchExecution(cmds));
    if (results.getValue("rer") instanceof RuleExecutionResult rer) {
      LOGGER.info("Results: {}", rer);
    } else {
      LOGGER.warn("No results found.");
    }
  }

  // This method is just a minor buildout of drools integration to set the rest up. It's temporary.
  public void sampleRun() {
    KieContainer firstContainer = getKieContainer("drl/sample1.drl");
    KieContainer secondContainer = getKieContainer("drl/sample2.drl");
    Tenant firstTenant = ImmutableTenant.builder().name("first").build();
    Tenant secondTenant = ImmutableTenant.builder().name("second").build();

    LOGGER.info("First Tenant / First Container");
    executeRules(firstContainer, firstTenant);
    LOGGER.info("Second Tenant / Second Container");
    executeRules(secondContainer, secondTenant);
    LOGGER.info("First Tenant / Second Container");
    executeRules(firstContainer, secondTenant);

  }

}
