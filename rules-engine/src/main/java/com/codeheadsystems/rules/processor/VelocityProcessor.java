package com.codeheadsystems.rules.processor;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.Velocity;
import com.codeheadsystems.rules.model.VelocityDefinition;
import java.util.Optional;

public class VelocityProcessor {

  private final VelocityDefinition definition;

  public VelocityProcessor(VelocityDefinition definition) {
    this.definition = definition;
  }

  public <T extends Number> Optional<Velocity<T>> from (JsonObject json) {
    final String varName = json.asString(definition.varPath());
    if (varName == null) {
      return Optional.empty();
    }
    throw new UnsupportedOperationException("UNFINISHED CODE");
  }

}
