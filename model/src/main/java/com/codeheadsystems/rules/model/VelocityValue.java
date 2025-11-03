package com.codeheadsystems.rules.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class VelocityValue<T extends Number> {

  final VelocityValueType type;

  private VelocityValue(final VelocityValueType type) {
    this.type = type;
  }
}
