package com.codeheadsystems.rules.processor.impl;

import static com.codeheadsystems.rules.processor.impl.VelocityProcessorTestHelper.createVelocityDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class VelocityProcessorEmptyIntegerTest {


  @Test
  void testValueFrom() {
    // Given
    final VelocityProcessorEmptyInteger processor = new VelocityProcessorEmptyInteger(createVelocityDefinition(null, null));

    // When
    final var result = processor.valueFrom(null);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().value()).isEqualByComparingTo(BigInteger.ONE);
  }

}