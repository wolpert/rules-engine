package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Velocity processor empty decimal.
 */
public class VelocityProcessorEmptyDecimal extends VelocityProcessorBase<BigDecimal> implements VelocityProcessor<BigDecimal> {

  public VelocityProcessorEmptyDecimal(VelocityDefinition velocityDefinition) {
    super(velocityDefinition);
  }

  @Override
  public Optional<VelocityValue<BigDecimal>> valueFrom(final JsonObject json) {
    return Optional.of(VelocityValue.VelocityValueBigDecimal.ONE);
  }

}
