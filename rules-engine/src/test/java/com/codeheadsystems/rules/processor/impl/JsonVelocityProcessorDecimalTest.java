package com.codeheadsystems.rules.processor.impl;

import static com.codeheadsystems.rules.processor.impl.VelocityProcessorTestHelper.createVelocityDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.model.VelocityVariableName;
import com.codeheadsystems.rules.processor.JsonVelocityProcessor;
import com.codeheadsystems.rules.test.FixtureHelper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonVelocityProcessorDecimalTest {

  private static final String BIG_DECIMAL_STRING = "12345.678901234567890123456789";
  private static final BigDecimal BIG_DECIMAL = new BigDecimal(BIG_DECIMAL_STRING);
  private static final BigDecimal JSON_SUCKS_DECIMAL = new BigDecimal("12345.678901234567");
  private static final VelocityVariableName VARIABLE_NAME = VelocityVariableName.of("you_found_me");

  private JsonObject jsonObject;

  @BeforeEach
  void setUp() {
    jsonObject = FixtureHelper.jsonObject("fixture/velocity_processor.json");
  }

  @Test
  void empty() {
    JsonVelocityProcessor<BigDecimal> processor = new JsonVelocityProcessorDecimal(createVelocityDefinition("/velocity/no_path/a_value", null));
    assertThat(processor.valueFrom(jsonObject)).isEmpty();
  }

  @Test
  void emptyWithVarName() {
    JsonVelocityProcessor<BigDecimal> processor = new JsonVelocityProcessorDecimal(createVelocityDefinition("/velocity/no_path/a_value", "/velocity/i_am_a_identifier"));
    assertThat(processor.variableNameFrom(jsonObject)).isPresent().hasValue(VARIABLE_NAME);
  }

  @Test
  void emptyWithNoVarName() {
    JsonVelocityProcessor<BigDecimal> processor = new JsonVelocityProcessorDecimal(createVelocityDefinition("/velocity/no_path/a_value", "/velocity/no_exist"));
    assertThat(processor.variableNameFrom(jsonObject)).isEmpty();
  }


  @Test
  void valueFrom() {
    JsonVelocityProcessor<BigDecimal> processor = new JsonVelocityProcessorDecimal(createVelocityDefinition("/velocity/dec_path/a_value", null));
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
    JsonVelocityProcessor<BigDecimal> processor = new JsonVelocityProcessorDecimal(createVelocityDefinition("/velocity/dec_path/a_str_value", null));
    VelocityValue<BigDecimal> value = processor.valueFrom(jsonObject).get();
    System.out.println("" + value.value());
    assertThat(processor.valueFrom(jsonObject))
        .isPresent()
        .hasValueSatisfying(v ->
            assertThat(v.value()).isEqualByComparingTo(BIG_DECIMAL)
        );
  }

}