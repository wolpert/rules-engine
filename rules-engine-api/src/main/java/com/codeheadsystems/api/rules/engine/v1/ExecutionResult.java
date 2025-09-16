package com.codeheadsystems.api.rules.engine.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableExecutionResult.class)
@JsonDeserialize(builder = ImmutableExecutionResult.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ExecutionResult {

  Map<String, String> RuleExecutionResponse();

}
