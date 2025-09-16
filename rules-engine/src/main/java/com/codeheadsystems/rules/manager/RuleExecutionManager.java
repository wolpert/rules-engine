package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.TenantContainer;
import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Rule execution manager.
 */
@Singleton
public class RuleExecutionManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionManager.class);

  private final TenantContainerManager tenantContainerManager;
  private final SessionManager sessionManager;
  private final KieServices kieServices = KieServices.Factory.get();

  @Inject
  public RuleExecutionManager(TenantContainerManager tenantContainerManager, final SessionManager sessionManager) {
    this.tenantContainerManager = tenantContainerManager;
    this.sessionManager = sessionManager;
    LOGGER.info("RuleExecutionManager(TenantContainerManager)");
  }

  public RuleExecutionResult executeRules(final Tenant tenant, final Facts facts) {
    TenantContainer tenantContainer = tenantContainerManager.tenantContainer(tenant);
    StatelessKieSession session = sessionManager.getKieSession(tenant, tenantContainer.kieContainer());
    final List<Command<?>> commands = getCommands(tenant, facts);
    ExecutionResults results = session.execute(kieServices.getCommands().newBatchExecution(commands));

    if (results.getValue("rer") instanceof RuleExecutionResult rer) {
      LOGGER.info("Results: {}", rer);
      return rer;
    } else {
      LOGGER.warn("No results found.");
      throw new IllegalStateException("No results found.");
    }
  }

  private List<Command<?>> getCommands(final Tenant tenant, final Facts facts) {
    ImmutableList.Builder<Command<?>> builder = ImmutableList.builder();
    builder.add(kieServices.getCommands().newSetGlobal("rer", new RuleExecutionResult(), true));
    builder.add(kieServices.getCommands().newInsert(tenant));
    builder.add(kieServices.getCommands().newInsert(facts.jsonObjects()));
    builder.add(kieServices.getCommands().newSetGlobal("eventId", facts.eventId(), true));
    builder.add(kieServices.getCommands().newFireAllRules());
    return builder.build();
  }

}
