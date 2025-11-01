package com.codeheadsystems.rules.resource;

import com.codeheadsystems.api.rules.engine.v1.EventProcessor;
import com.codeheadsystems.api.rules.engine.v1.ExecutionResult;
import com.codeheadsystems.rules.converter.ExecutionResultConverter;
import com.codeheadsystems.rules.converter.FactsConverter;
import com.codeheadsystems.rules.manager.ExecutionRequestManager;
import com.codeheadsystems.rules.manager.RuleExecutionManager;
import com.codeheadsystems.rules.model.EventIdentifier;
import com.codeheadsystems.rules.model.EventType;
import com.codeheadsystems.rules.model.ExecutionRequest;
import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.RuleExecutionResult;
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
  private final ExecutionRequestManager executionRequestManager;

  /**
   * Instantiates a new Event resource.
   *
   * @param ruleExecutionManager     the rule execution manager
   * @param factsConverter           the facts converter
   * @param executionResultConverter the execution result converter
   * @param executionRequestManager  the execution request manager
   */
  @Inject
  public EventProcessorResource(final RuleExecutionManager ruleExecutionManager,
                                final FactsConverter factsConverter,
                                final ExecutionResultConverter executionResultConverter,
                                final ExecutionRequestManager executionRequestManager) {
    this.ruleExecutionManager = ruleExecutionManager;
    this.factsConverter = factsConverter;
    this.executionResultConverter = executionResultConverter;
    this.executionRequestManager = executionRequestManager;
  }

  @Override
  public ExecutionResult execute(final String tenantName,
                                 final String eventName,
                                 final String versionName,
                                 final String eventId,
                                 final String jsonEventData) {
    final Tenant tenant = Tenant.of(tenantName);
    final EventType eventType = EventType.of(eventName);
    final Version version = Version.of(versionName);
    final EventIdentifier eventIdentifier = EventIdentifier.of(eventId);

    // TODO: validate the eventId hasn't already been processed.
    final ExecutionRequest<JsonObject> executionRequest = executionRequestManager.create(tenant, eventType, version, eventIdentifier, jsonEventData);

    final RuleExecutionResult ruleExecutionResult = ruleExecutionManager.execute(executionRequest);
    return executionResultConverter.convert(ruleExecutionResult);
  }
}
