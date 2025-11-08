package com.codeheadsystems.rules.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The enum Velocity value type.
 */
public enum VelocityValueType {

  /**
   * Integer velocity value type.
   */
  INTEGER(BigInteger.class),
  /**
   * Float velocity value type.
   */
  DECIMAL(BigDecimal.class);

  private final Class<? extends Number> clazz;

  <T extends Number> VelocityValueType(final Class<T> numberClass) {
    clazz = numberClass;
  }

  /**
   * Number class class.
   *
   * @param <T> the type parameter
   * @return the class
   */
  @SuppressWarnings("unchecked")
  public <T extends Number> Class<T> numberClass() {
    return (Class<T>) clazz;
  }
}
