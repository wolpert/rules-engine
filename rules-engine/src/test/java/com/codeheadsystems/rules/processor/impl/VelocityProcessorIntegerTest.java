package com.codeheadsystems.rules.processor.impl;

import static com.codeheadsystems.rules.processor.impl.VelocityProcessorTestHelper.createVelocityDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityVariableName;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import com.codeheadsystems.rules.test.FixtureHelper;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VelocityProcessorIntegerTest {

  private static final BigInteger BIG_INTEGER = new BigInteger("123456789012345678901234567890");
  private static final VelocityVariableName VARIABLE_NAME = VelocityVariableName.of("you_found_me");

  private JsonObject jsonObject;

  @BeforeEach
  void setUp() {
    jsonObject = FixtureHelper.jsonObject("fixture/velocity_processor.json");
  }

  @Test
  void empty() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger(createVelocityDefinition("/velocity/no_path/a_value", null));
    assertThat(processor.valueFrom(jsonObject)).isEmpty();
  }

  @Test
  void emptyWithVarName() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger(createVelocityDefinition("/velocity/no_path/a_value", "/velocity/i_am_a_identifier"));
    assertThat(processor.variableNameFrom(jsonObject)).isPresent().hasValue(VARIABLE_NAME);
  }

  @Test
  void emptyWithNoVarName() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger(createVelocityDefinition("/velocity/no_path/a_value", "/velocity/no_exist"));
    assertThat(processor.variableNameFrom(jsonObject)).isEmpty();
  }

  @Test
  void valueFrom() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger(createVelocityDefinition("/velocity/int_path/a_value", null));
    assertThat(processor.valueFrom(jsonObject))
        .isPresent()
        .hasValueSatisfying(v ->
            assertThat(v.value()).isEqualByComparingTo(BIG_INTEGER)
        );
  }
  @Test
  void valueFromString() {
    VelocityProcessor<BigInteger> processor = new VelocityProcessorInteger(createVelocityDefinition("/velocity/int_path/a_str_value", null));
    assertThat(processor.valueFrom(jsonObject))
        .isPresent()
        .hasValueSatisfying(v ->
            assertThat(v.value()).isEqualByComparingTo(BIG_INTEGER)
        );
  }

}