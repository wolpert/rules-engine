package com.codeheadsystems.rules.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VelocityValueTest {

  @Test
  void bigInteger_additionAndImmutability() {
    var a = new VelocityValue.VelocityValueBigInteger(new BigInteger("12345678901234567890"));
    var b = new VelocityValue.VelocityValueBigInteger(new BigInteger("10"));

    var result = a.add(b);

    assertThat(result).isInstanceOf(VelocityValue.VelocityValueBigInteger.class);
    assertThat(result.value()).isEqualByComparingTo(new BigInteger("12345678901234567900"));
    // originals unchanged
    assertThat(a.value()).isEqualByComparingTo(new BigInteger("12345678901234567890"));
    assertThat(b.value()).isEqualByComparingTo(new BigInteger("10"));
  }

  @Test
  void bigDecimal_additionDifferentScales() {
    var a = new VelocityValue.VelocityValueBigDecimal(new BigDecimal("1.200"));
    var b = new VelocityValue.VelocityValueBigDecimal(new BigDecimal("2.03"));

    var result = a.add(b);

    assertThat(result).isInstanceOf(VelocityValue.VelocityValueBigDecimal.class);
    assertThat(result.value()).isEqualByComparingTo(new BigDecimal("3.230"));
    // originals unchanged
    assertThat(a.value()).isEqualByComparingTo(new BigDecimal("1.200"));
    assertThat(b.value()).isEqualByComparingTo(new BigDecimal("2.03"));
  }

  @Test
  void add_nullThrowsNullPointerException() {
    var a = new VelocityValue.VelocityValueBigInteger(BigInteger.ONE);
    assertThatThrownBy(() -> a.add(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void bigDecimal_zeroAndNegative() {
    var zero = new VelocityValue.VelocityValueBigDecimal(BigDecimal.ZERO);
    var neg = new VelocityValue.VelocityValueBigDecimal(new BigDecimal("-5.5"));

    var res = zero.add(neg);
    assertThat(res.value()).isEqualByComparingTo(new BigDecimal("-5.5"));

    var res2 = neg.add(zero);
    assertThat(res2.value()).isEqualByComparingTo(new BigDecimal("-5.5"));
  }
}
