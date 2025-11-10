package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.ExecutionRequest;
import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.RuleExecutionResult;
import com.codeheadsystems.rules.model.RuleSet;
import com.codeheadsystems.rules.model.Velocity;
import com.codeheadsystems.rules.model.VelocitySet;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class Orchestrator {

  private static final Logger LOGGER = LoggerFactory.getLogger(Orchestrator.class);


  @Inject
  public Orchestrator() {
    LOGGER.info("Orchestrator()");
  }

  public RuleExecutionResult execute(ExecutionRequest<JsonObject> executionRequest) {
    LOGGER.debug("execute({}) -->", executionRequest);
    final VelocitySet velocitySet = loadVelocitySet(executionRequest);
    final RuleSet ruleSet = loadRuleSet(executionRequest);
    final Velocity<?>[] velocities = generateVelocities(velocitySet, executionRequest);
    final Velocity<?>[] aggregateVelocities = loadAggregateVelocities(velocities, executionRequest);
    final RuleExecutionResult ruleExecutionResult = executeRules(ruleSet, executionRequest, aggregateVelocities);
    uploadEventData(executionRequest, ruleExecutionResult);
    LOGGER.debug("execute({}) <--", executionRequest);
    return ruleExecutionResult;
  }

  private VelocitySet loadVelocitySet(final ExecutionRequest<JsonObject> executionRequest) {
    LOGGER.trace("loadVelocitySet({})", executionRequest.executionRequestId());
    throw new UnsupportedOperationException("loadVelocitySet : Not implemented yet.");
  }

  private RuleSet loadRuleSet(final ExecutionRequest<JsonObject> executionRequest) {
    LOGGER.trace("loadRuleSet({})", executionRequest.executionRequestId());
    throw new UnsupportedOperationException("loadRuleSet : Not implemented yet.");
  }

  private Velocity<?>[] generateVelocities(final VelocitySet velocitySet, final ExecutionRequest<JsonObject> executionRequest) {
    LOGGER.trace("generateVelocities({})", executionRequest.executionRequestId());
    throw new UnsupportedOperationException("generateVelocities : Not implemented yet.");
  }

  private Velocity<?>[] loadAggregateVelocities(final Velocity<?>[] velocities, final ExecutionRequest<JsonObject> executionRequest) {
    LOGGER.trace("loadAggregateVelocities({})", executionRequest.executionRequestId());
    throw new UnsupportedOperationException("loadAggregateVelocities : Not implemented yet.");
  }

  private RuleExecutionResult executeRules(final RuleSet ruleSet, final ExecutionRequest<JsonObject> executionRequest, final Velocity<?>[] aggregateVelocities) {
    LOGGER.trace("executeRules({})", executionRequest.executionRequestId());
    throw new UnsupportedOperationException("executeRules : Not implemented yet.");
  }

  private void uploadEventData(final ExecutionRequest<JsonObject> executionRequest, final RuleExecutionResult ruleExecutionResult) {
    LOGGER.trace("uploadEventData({})", executionRequest.executionRequestId());
    throw new UnsupportedOperationException("uploadEventData : Not implemented yet.");
  }


}
