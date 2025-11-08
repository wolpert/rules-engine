package com.codeheadsystems.rules.processor.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import com.codeheadsystems.rules.test.FixtureHelper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VelocityProcessorDecimalTest {

  private static final String BIG_DECIMAL_STRING = "12345.678901234567890123456789";
  private static final BigDecimal BIG_DECIMAL = new BigDecimal(BIG_DECIMAL_STRING);
  private static final BigDecimal JSON_SUCKS_DECIMAL = new BigDecimal("12345.678901234567");

  private JsonObject jsonObject;

  @BeforeEach
  void setUp() {
    jsonObject = FixtureHelper.jsonObject("fixture/velocity_processor.json");
  }

  @Test
  void empty() {
    VelocityProcessor<BigDecimal> processor = new VelocityProcessorDecimal("/velocity/no_path/a_value");
    assertThat(processor.valueFrom(jsonObject)).isEmpty();
  }

  @Test
  void valueFrom() {
    VelocityProcessor<BigDecimal> processor = new VelocityProcessorDecimal("/velocity/dec_path/a_value");
    VelocityValue<BigDecimal> value = processor.valueFrom(jsonObject).get();
    System.out.println("" + value.value());
    assertThat(processor.valueFrom(jsonObject))
        .isPresent()
        .hasValueSatisfying(v ->
            assertThat(v.value()).isEqualByComparingTo(JSON_SUCKS_DECIMAL)
        );
  }

  @Test
  void valueFromString() {
    VelocityProcessor<BigDecimal> processor = new VelocityProcessorDecimal("/velocity/dec_path/a_str_value");
    VelocityValue<BigDecimal> value = processor.valueFrom(jsonObject).get();
    System.out.println("" + value.value());
    assertThat(processor.valueFrom(jsonObject))
        .isPresent()
        .hasValueSatisfying(v ->
            assertThat(v.value()).isEqualByComparingTo(BIG_DECIMAL)
        );
  }

}