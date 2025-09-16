package com.codeheadsystems.rules.resource;

import com.codeheadsystems.api.rules.engine.v1.Event;
import com.codeheadsystems.api.rules.engine.v1.ExecutionResult;
import com.codeheadsystems.rules.converter.ExecutionResultConverter;
import com.codeheadsystems.rules.converter.FactsConverter;
import com.codeheadsystems.rules.manager.RuleExecutionManager;
import com.codeheadsystems.rules.manager.TenantManager;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.server.resource.JerseyResource;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EventResource implements Event, JerseyResource {

  private final RuleExecutionManager ruleExecutionManager;
  private final TenantManager tenantManager;
  private final FactsConverter factsConverter;
  private final ExecutionResultConverter executionResultConverter;

  @Inject
  public EventResource(final RuleExecutionManager ruleExecutionManager, final TenantManager tenantManager, final FactsConverter factsConverter, final ExecutionResultConverter executionResultConverter) {
    this.ruleExecutionManager = ruleExecutionManager;
    this.tenantManager = tenantManager;
    this.factsConverter = factsConverter;
    this.executionResultConverter = executionResultConverter;
  }

  @Override
  public ExecutionResult execute(final String tenantName,
                                 final String eventType,
                                 final String eventId,
                                 final String jsonEventData) {
    final Tenant tenant = tenantManager.getTenant(tenantName)
        .orElseThrow(() -> new IllegalArgumentException("No such tenant: " + tenantName));
    // TODO: Validate the caller can use this tenant.
    // TODO: ignoring the eventType for now.
    final Facts facts = factsConverter.convert(eventId, jsonEventData);
    final RuleExecutionResult ruleExecutionResult = ruleExecutionManager.executeRules(tenant, facts);
    return executionResultConverter.convert(ruleExecutionResult);
  }
}
