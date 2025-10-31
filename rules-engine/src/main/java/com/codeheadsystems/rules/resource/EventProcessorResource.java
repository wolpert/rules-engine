package com.codeheadsystems.rules.resource;

import com.codeheadsystems.api.rules.engine.v1.EventProcessor;
import com.codeheadsystems.api.rules.engine.v1.ExecutionResult;
import com.codeheadsystems.rules.converter.ExecutionResultConverter;
import com.codeheadsystems.rules.converter.FactsConverter;
import com.codeheadsystems.rules.manager.RuleExecutionManager;
import com.codeheadsystems.rules.model.Event;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableRuleSetIdentifier;
import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.Version;
import com.codeheadsystems.server.resource.JerseyResource;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Event resource.
 */
@Singleton
public class EventProcessorResource implements EventProcessor, JerseyResource {

  private final RuleExecutionManager ruleExecutionManager;
  private final FactsConverter factsConverter;
  private final ExecutionResultConverter executionResultConverter;

  /**
   * Instantiates a new Event resource.
   *
   * @param ruleExecutionManager     the rule execution manager
   * @param factsConverter           the facts converter
   * @param executionResultConverter the execution result converter
   */
  @Inject
  public EventProcessorResource(final RuleExecutionManager ruleExecutionManager,
                                final FactsConverter factsConverter,
                                final ExecutionResultConverter executionResultConverter) {
    this.ruleExecutionManager = ruleExecutionManager;
    this.factsConverter = factsConverter;
    this.executionResultConverter = executionResultConverter;
  }

  @Override
  public ExecutionResult execute(final String tenantName,
                                 final String eventName,
                                 final String versionName,
                                 final String eventId,
                                 final String jsonEventData) {
    final Tenant tenant = Tenant.of(tenantName);
    final Event event = Event.of(eventName);
    final Version version = Version.of(versionName);
    final RuleSetIdentifier ruleSetIdentifier = ImmutableRuleSetIdentifier.builder()
        .tenant(tenant)
        .event(event)
        .eventVersion(version)
        .build();
    // TODO: validate the eventId hasn't already been processed.
    final Facts<JsonObject> facts = factsConverter.convert(eventId, jsonEventData);
    final RuleExecutionResult ruleExecutionResult = ruleExecutionManager.executeRules(ruleSetIdentifier, facts);
    return executionResultConverter.convert(ruleExecutionResult);
  }
}
