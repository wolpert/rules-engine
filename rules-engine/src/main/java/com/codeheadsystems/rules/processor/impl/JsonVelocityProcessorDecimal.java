package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.JsonVelocityProcessor;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Velocity processor decimal.
 */
public class JsonVelocityProcessorDecimal extends JsonVelocityProcessor<BigDecimal> {

  /**
   * Instantiates a new Velocity processor decimal.
   *
   * @param definition the definition
   */
  public JsonVelocityProcessorDecimal(VelocityDefinition definition) {
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
