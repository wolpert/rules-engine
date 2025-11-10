package com.codeheadsystems.rules.model;

import java.time.Instant;
import java.util.UUID;
import org.immutables.value.Value;

/**
 * The interface Execution request.
 *
 * @param <T> the type parameter
 */
@Value.Immutable
public interface ExecutionRequest<T> {

  /**
   * Execution request id uuid.
   *
   * @return the uuid
   */
  UUID executionRequestId();

  /**
   * Rule set identifier rule set identifier.
   *
   * @return the rule set identifier
   */
  RuleSetIdentifier ruleSetIdentifier();

  /**
   * Event identifier event identifier.
   *
   * @return the event identifier
   */
  EventIdentifier eventIdentifier();

  /**
   * Facts facts.
   *
   * @return the facts
   */
  Facts<T> facts();

  /**
   * Execution environment execution environment.
   *
   * @return the execution environment
   */
  ExecutionEnvironment executionEnvironment();

  /**
   * Request timestamp instant.
   *
   * @return the instant
   */
  Instant requestTimestamp();

  /**
   * Shadow mode boolean.
   *
   * @return the boolean
   */
  default Boolean shadowMode() {
    return Boolean.FALSE;
  }

}
