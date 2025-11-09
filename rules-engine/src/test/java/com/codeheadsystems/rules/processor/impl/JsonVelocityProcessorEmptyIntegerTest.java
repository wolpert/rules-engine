package com.codeheadsystems.rules.processor.impl;

import static com.codeheadsystems.rules.processor.impl.VelocityProcessorTestHelper.createVelocityDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class JsonVelocityProcessorEmptyIntegerTest {


  @Test
  void testValueFrom() {
    // Given
    final JsonVelocityProcessorEmptyInteger processor = new JsonVelocityProcessorEmptyInteger(createVelocityDefinition(null, null));

    // When
    final var result = processor.valueFrom(null);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().value()).isEqualByComparingTo(BigInteger.ONE);
  }

}