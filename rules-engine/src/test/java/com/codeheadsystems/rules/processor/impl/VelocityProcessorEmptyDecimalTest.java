package com.codeheadsystems.rules.processor.impl;

import static com.codeheadsystems.rules.processor.impl.VelocityProcessorTestHelper.createVelocityDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class VelocityProcessorEmptyDecimalTest {

  @Test
  void testValueFrom() {
    // Given
    final VelocityProcessorEmptyDecimal processor = new VelocityProcessorEmptyDecimal(createVelocityDefinition(null, null));

    // When
    final var result = processor.valueFrom(null);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().value()).isEqualByComparingTo(BigDecimal.ONE);
  }

}