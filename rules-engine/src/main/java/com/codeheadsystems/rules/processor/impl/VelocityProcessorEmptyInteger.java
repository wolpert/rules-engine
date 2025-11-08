package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import java.math.BigInteger;
import java.util.Optional;

/**
 * The type Velocity processor empty integer.
 */
public class VelocityProcessorEmptyInteger implements VelocityProcessor<BigInteger> {

  @Override
  public Optional<VelocityValue<BigInteger>> valueFrom(final JsonObject json) {
    return Optional.of(VelocityValue.VelocityValueBigInteger.ONE);
  }

}
