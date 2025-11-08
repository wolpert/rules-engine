package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import java.math.BigInteger;
import java.util.Optional;

/**
 * The type Velocity processor integer.
 */
public class VelocityProcessorInteger extends VelocityProcessorBase<BigInteger> implements VelocityProcessor<BigInteger> {

  public VelocityProcessorInteger(VelocityDefinition definition) {
    super(definition);
  }

  @Override
  public Optional<VelocityValue<BigInteger>> valueFrom(final JsonObject json) {
    if (json.exists(valPath)) { // get the value.\
      return Optional.of(new VelocityValue.VelocityValueBigInteger(json.asBigInteger(valPath)));
    } else {
      return Optional.empty();
    }
  }

}
