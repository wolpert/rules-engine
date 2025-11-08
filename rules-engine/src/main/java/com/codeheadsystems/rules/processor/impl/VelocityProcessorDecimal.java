package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Velocity processor decimal.
 */
public class VelocityProcessorDecimal extends VelocityProcessorBase<BigDecimal> implements VelocityProcessor<BigDecimal> {

  public VelocityProcessorDecimal(VelocityDefinition definition) {
    super(definition);
  }

  @Override
  public Optional<VelocityValue<BigDecimal>> valueFrom(final JsonObject json) {
    if (json.exists(valPath)) { // get the value.
      return Optional.of(new VelocityValue.VelocityValueBigDecimal(json.asBigDecimal(valPath)));
    } else {
      return Optional.empty();
    }
  }

}
