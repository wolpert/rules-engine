package com.codeheadsystems.rules.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.immutables.value.Value;

/**
 * The interface Velocity.
 *
 * @param <T> the type parameter
 */
@Value.Immutable
public interface Velocity<T extends Number> {

  /**
   * Of velocity.
   *
   * @param <T>        the type parameter
   * @param definition the definition
   * @param value      the value
   * @return the velocity
   */
  static <T extends Number> Velocity<T> of(final VelocityDefinition definition,
                                           final T value) {
    Class<T> definitionType = definition.type().numberClass();
    if (!value.getClass().equals(definitionType)) {
      throw new IllegalArgumentException("Value type " + value.getClass() +
          " does not match definition type " + definitionType);
    }
    return ImmutableVelocity.<T>builder()
        .definition(definition)
        .value(value)
        .valueType(definitionType)
        .build();
  }

  /**
   * Add velocity.
   *
   * @param additionalValue the additional value
   * @return the velocity
   */
  @SuppressWarnings("unchecked")
  default Velocity<T> add(final T additionalValue) {
    if (additionalValue instanceof BigInteger bigInteger) {
      return ImmutableVelocity.copyOf(this).withValue((T) bigInteger.add((BigInteger) value()));
    } else if (additionalValue instanceof BigDecimal bigDecimal) {
      return ImmutableVelocity.copyOf(this).withValue((T) bigDecimal.add((BigDecimal) value()));
    } else {
      throw new IllegalArgumentException("Unsupported value type " + additionalValue.getClass());
    }
  }

  /**
   * Add velocity.
   *
   * @param other the other
   * @return the velocity
   */
  default Velocity<T> add(final Velocity<T> other) {
    return add(other.value());
  }

  /**
   * Velocity definition.
   *
   * @return the velocity definition.
   */
  VelocityDefinition definition();

  /**
   * Value t.
   *
   * @return the t
   */
  T value();

  /**
   * Value type class.
   *
   * @return the class
   */
  Class<T> valueType();

}
