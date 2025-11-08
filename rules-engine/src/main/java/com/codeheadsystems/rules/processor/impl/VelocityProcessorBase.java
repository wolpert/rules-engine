package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityVariableName;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import java.util.Optional;

public abstract class VelocityProcessorBase<T extends Number> implements VelocityProcessor<T> {

  protected final String valPath;
  protected final String varNamePath;

  public VelocityProcessorBase(VelocityDefinition velocityDefinition) {
    this.valPath = velocityDefinition.valPath().orElse(null);
    this.varNamePath = velocityDefinition.varPath();
  }

  @Override
  public Optional<VelocityVariableName> variableNameFrom(final JsonObject json) {
    if (json.exists(varNamePath)) {
      return Optional.of(VelocityVariableName.of(json.asString(varNamePath)));
    } else {
      return Optional.empty();
    }
  }
}
