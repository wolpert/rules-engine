package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import java.math.BigInteger;
import java.util.Optional;

/**
 * The type Velocity processor integer.
 */
public class VelocityProcessorInteger implements VelocityProcessor<BigInteger> {

  private final String path;

  /**
   * Instantiates a new Velocity processor integer.
   *
   * @param path the path
   */
  public VelocityProcessorInteger(final String path) {
    this.path = path;
  }

  @Override
  public Optional<VelocityValue<BigInteger>> valueFrom(final JsonObject json) {
    if (json.exists(path)) { // get the value.\
      return Optional.of(new VelocityValue.VelocityValueBigInteger(json.asBigInteger(path)));
    } else {
      return Optional.empty();
    }
  }

}
