package com.codeheadsystems.rules.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public enum VelocityValueType {

  INTEGER(BigInteger.class), FLOAT(BigDecimal.class);

  private final Class<? extends Number> clazz;

  <T extends Number> VelocityValueType(final Class<T> numberClass) {
    clazz = numberClass;
  }

  @SuppressWarnings("unchecked")
  public <T extends Number> Class<T> numberClass() {
    return (Class<T>) clazz;
  }
}
