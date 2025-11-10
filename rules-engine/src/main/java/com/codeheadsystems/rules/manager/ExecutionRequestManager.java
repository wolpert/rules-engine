package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.converter.FactsConverter;
import com.codeheadsystems.rules.model.EventIdentifier;
import com.codeheadsystems.rules.model.EventType;
import com.codeheadsystems.rules.model.ExecutionEnvironment;
import com.codeheadsystems.rules.model.ExecutionRequest;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableExecutionRequest;
import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.Version;
import java.time.Clock;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Execution request manager.
 */
@Singleton
public class ExecutionRequestManager {

  private final FactsConverter factsConverter;
  private final ExecutionEnvironment executionEnvironment;
  private final UuidManager uuidManager;
  private final Clock clock;

  /**
   * Instantiates a new Execution request manager.
   *
   * @param factsConverter       the facts converter
   * @param executionEnvironment the execution environment
   * @param clock                the clock
   */
  @Inject
  public ExecutionRequestManager(final FactsConverter factsConverter, final ExecutionEnvironment executionEnvironment, final UuidManager uuidManager, final Clock clock) {
    this.factsConverter = factsConverter;
    this.executionEnvironment = executionEnvironment;
    this.uuidManager = uuidManager;
    this.clock = clock;
  }

  /**
   * Create execution request.
   *
   * @param tenant        the tenant
   * @param eventType     the event type
   * @param version       the version
   * @param eventId       the event id
   * @param jsonEventData the json event data
   * @return the execution request
   */
  public ExecutionRequest<JsonObject> create(final Tenant tenant,
                                             final EventType eventType,
                                             final Version version,
                                             final EventIdentifier eventId,
                                             final String jsonEventData) {
    final RuleSetIdentifier ruleSetIdentifier = RuleSetIdentifier.of(tenant, eventType, version);
    final Facts<JsonObject> facts = factsConverter.convert(eventId.value(), jsonEventData);
    return ImmutableExecutionRequest.<JsonObject>builder()
        .executionRequestId(uuidManager.generate())
        .executionEnvironment(executionEnvironment)
        .facts(facts)
        .eventIdentifier(eventId)
        .requestTimestamp(clock.instant())
        .ruleSetIdentifier(ruleSetIdentifier)
        .build();
  }


}
