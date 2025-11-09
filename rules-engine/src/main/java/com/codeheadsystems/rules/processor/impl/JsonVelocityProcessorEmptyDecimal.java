package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.JsonVelocityProcessor;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Velocity processor empty decimal.
 */
public class JsonVelocityProcessorEmptyDecimal extends JsonVelocityProcessor<BigDecimal> {

  /**
   * Instantiates a new Velocity processor empty decimal.
   *
   * @param velocityDefinition the velocity definition
   */
  public JsonVelocityProcessorEmptyDecimal(VelocityDefinition velocityDefinition) {
    super(velocityDefinition);
  }

  @Override
  public Optional<VelocityValue<BigDecimal>> valueFrom(final JsonObject json) {
    return Optional.of(VelocityValue.VelocityValueBigDecimal.ONE);
  }

}
