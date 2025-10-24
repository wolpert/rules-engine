package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.RuleExecutionContainer;
import com.codeheadsystems.rules.model.RuleExecutionRequest;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.Tenant;
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
  private static final String RESULT = "executionResult";
  private static final String EVENT_ID = "eventId";

  private final RuleContainerManager ruleContainerManager;
  private final RuleExecutionRequestManager ruleExecutionRequestManager;
  private final SessionManager sessionManager;
  private final KieServices kieServices = KieServices.Factory.get();

  /**
   * Instantiates a new Rule execution manager.
   *
   * @param ruleContainerManager the tenant container manager
   * @param sessionManager       the session manager
   */
  @Inject
  public RuleExecutionManager(final RuleContainerManager ruleContainerManager,
                              final RuleExecutionRequestManager ruleExecutionRequestManager,
                              final SessionManager sessionManager) {
    this.ruleContainerManager = ruleContainerManager;
    this.ruleExecutionRequestManager = ruleExecutionRequestManager;
    this.sessionManager = sessionManager;
    LOGGER.info("RuleExecutionManager(TenantContainerManager)");
  }

  /**
   * Execute rules rule execution result.
   *
   * @param tenant the tenant
   * @param facts  the facts
   * @return the rule execution result
   */
  public RuleExecutionResult executeRules(final Tenant tenant, final Facts facts) {
    final RuleExecutionRequest request = ruleExecutionRequestManager.forTenant(tenant);
    final RuleExecutionContainer ruleExecutionContainer = ruleContainerManager.ruleContainer(request);
    final StatelessKieSession session = sessionManager.getKieSession(tenant, ruleExecutionContainer.kieContainer());
    final List<Command<?>> commands = getCommands(tenant, facts);
    commands.forEach(c -> LOGGER.debug("Command: {}", c));
    ExecutionResults results = session.execute(kieServices.getCommands().newBatchExecution(commands));

    if (results.getValue(RESULT) instanceof RuleExecutionResult rer) {
      LOGGER.info("Results: {}", rer);
      return rer;
    } else {
      LOGGER.warn("No results found.");
      throw new IllegalStateException("No results found.");
    }
  }

  private List<Command<?>> getCommands(final Tenant tenant, final Facts facts) {
    ImmutableList.Builder<Command<?>> builder = ImmutableList.builder();
    builder.add(kieServices.getCommands().newSetGlobal(RESULT, new RuleExecutionResult(), true));
    builder.add(kieServices.getCommands().newInsert(tenant));
    facts.jsonObjects().forEach(jo -> builder.add(kieServices.getCommands().newInsert(jo)));
    builder.add(kieServices.getCommands().newSetGlobal(EVENT_ID, facts.eventId(), true));
    builder.add(kieServices.getCommands().newFireAllRules());
    return builder.build();
  }

}
