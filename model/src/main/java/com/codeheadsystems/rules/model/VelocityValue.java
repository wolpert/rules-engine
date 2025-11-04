package com.codeheadsystems.rules.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface VelocityValue<E extends Number> {

  VelocityValue<E> add(VelocityValue<E> other);

  E value();

  record VelocityValueBigInteger(BigInteger value) implements VelocityValue<BigInteger> {

    @Override
      public VelocityValue<BigInteger> add(VelocityValue<BigInteger> other) {
        return new VelocityValueBigInteger(this.value.add(other.value()));
      }
    }

  record VelocityValueBigDecimal(BigDecimal value) implements VelocityValue<BigDecimal> {

    @Override
      public VelocityValue<BigDecimal> add(VelocityValue<BigDecimal> other) {
        return new VelocityValueBigDecimal(this.value.add(other.value()));
      }
    }

}
