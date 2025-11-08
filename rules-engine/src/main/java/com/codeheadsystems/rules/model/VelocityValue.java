package com.codeheadsystems.rules.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * The interface Velocity value, which are immutable.
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
   * Add velocity value.
   *
   * @param otherValue the other value
   * @return the velocity value
   */
  VelocityValue<E> add(E otherValue);

  /**
   * Value e.
   *
   * @return the e
   */
  E value();

  /**
   * The type Velocity value big integer.
   */
  record VelocityValueBigInteger(BigInteger value) implements VelocityValue<BigInteger> {

    /**
     * The constant ONE.
     */
    public static final VelocityValue<BigInteger> ONE = new VelocityValueBigInteger(BigInteger.ONE);

    public VelocityValueBigInteger {
      Objects.requireNonNull(value, "value");
    }

    /**
     * Instantiates a new Velocity value big integer.
     *
     * @param value the value
     */
    public VelocityValueBigInteger(String value) {
      this(new BigInteger(Objects.requireNonNull(value)));
    }

    @Override
    public VelocityValue<BigInteger> add(VelocityValue<BigInteger> other) {
      if (other == null) {
        return this;
      }
      return add(other.value());
    }

    @Override
    public VelocityValue<BigInteger> add(final BigInteger otherValue) {
      if (otherValue == null) {
        return this;
      }
      return new VelocityValueBigInteger(this.value.add(otherValue));
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
     * The constant ONE.
     */
    public static final VelocityValue<BigDecimal> ONE = new VelocityValueBigDecimal(BigDecimal.ONE);

    public VelocityValueBigDecimal {
      Objects.requireNonNull(value, "value");
    }

    /**
     * Instantiates a new Velocity value big decimal.
     *
     * @param value the value
     */
    public VelocityValueBigDecimal(String value) {
      this(new BigDecimal(Objects.requireNonNull(value)));
    }

    @Override
    public VelocityValue<BigDecimal> add(VelocityValue<BigDecimal> other) {
      if (other == null) {
        return this;
      }
      return add(other.value());
    }

    @Override
    public VelocityValue<BigDecimal> add(final BigDecimal otherValue) {
      if (otherValue == null) {
        return this;
      }
      return new VelocityValueBigDecimal(this.value.add(otherValue));
    }

    @Override
    public String toString() {
      return value().toString();
    }
  }

}
