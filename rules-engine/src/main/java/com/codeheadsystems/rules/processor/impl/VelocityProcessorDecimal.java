package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Velocity processor decimal.
 */
public class VelocityProcessorDecimal implements VelocityProcessor<BigDecimal> {

  private final String path;

  /**
   * Instantiates a new Velocity processor decimal.
   *
   * @param path the path
   */
  public VelocityProcessorDecimal(final String path) {
    this.path = path;
  }

  @Override
  public Optional<VelocityValue<BigDecimal>> valueFrom(final JsonObject json) {
    if (json.exists(path)) { // get the value.\
      return Optional.of(new VelocityValue.VelocityValueBigDecimal(json.asBigDecimal(path)));
    } else {
      return Optional.empty();
    }
  }

}
