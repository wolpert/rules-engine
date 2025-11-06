package com.codeheadsystems.rules.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The interface Velocity value.
 *
 * @param <E> the type parameter
 */
public interface VelocityValue<E extends Number> {

  /**
   * Add velocity value.
   *
   * @param other the other
   * @return the velocity value
   */
  VelocityValue<E> add(VelocityValue<E> other);

  /**
   * Value e.
   *
   * @return the e
   */
  E value();

  Class<E> valueClass();

  /**
   * The type Velocity value big integer.
   */
  record VelocityValueBigInteger(BigInteger value) implements VelocityValue<BigInteger> {

    /**
     * Instantiates a new Velocity value big integer.
     *
     * @param value the value
     */
    public VelocityValueBigInteger(String value) {
      this(new BigInteger(value));
    }

    @Override
    public VelocityValue<BigInteger> add(VelocityValue<BigInteger> other) {
      return new VelocityValueBigInteger(this.value.add(other.value()));
    }

    @Override
    public Class<BigInteger> valueClass() {
      return BigInteger.class;
    }

    @Override
    public String toString() {
      return value().toString();
    }
  }

  /**
   * The type Velocity value big decimal.
   */
  record VelocityValueBigDecimal(BigDecimal value) implements VelocityValue<BigDecimal> {

    /**
     * Instantiates a new Velocity value big decimal.
     *
     * @param value the value
     */
    public VelocityValueBigDecimal(String value) {
      this(new BigDecimal(value));
    }

    @Override
    public VelocityValue<BigDecimal> add(VelocityValue<BigDecimal> other) {
      return new VelocityValueBigDecimal(this.value.add(other.value()));
    }

    @Override
    public Class<BigDecimal> valueClass() {
      return BigDecimal.class;
    }

    @Override
    public String toString() {
      return value().toString();
    }
  }

}
