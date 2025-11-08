package com.codeheadsystems.rules.processor.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import com.codeheadsystems.rules.test.FixtureHelper;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VelocityProcessorIntegerTest {

  private static final BigInteger BIG_INTEGER = new BigInteger("123456789012345678901234567890");

  private JsonObject jsonObject;

  @BeforeEach
  void setUp() {
    jsonObject = FixtureHelper.jsonObject("fixture/velocity_processor.json");
  }

  @Test
  void empty() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger("/velocity/no_path/a_value");
    assertThat(processor.valueFrom(jsonObject)).isEmpty();
  }

  @Test
  void valueFrom() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger("/velocity/int_path/a_value");
    assertThat(processor.valueFrom(jsonObject))
        .isPresent()
        .hasValueSatisfying(v ->
            assertThat(v.value()).isEqualByComparingTo(BIG_INTEGER)
        );
  }
  @Test
  void valueFromString() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger("/velocity/int_path/a_str_value");
    assertThat(processor.valueFrom(jsonObject))
        .isPresent()
        .hasValueSatisfying(v ->
            assertThat(v.value()).isEqualByComparingTo(BIG_INTEGER)
        );
  }

}