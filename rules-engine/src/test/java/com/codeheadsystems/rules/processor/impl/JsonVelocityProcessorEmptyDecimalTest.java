package com.codeheadsystems.rules.processor.impl;

import static com.codeheadsystems.rules.processor.impl.VelocityProcessorTestHelper.createVelocityDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class JsonVelocityProcessorEmptyDecimalTest {

  @Test
  void testValueFrom() {
    // Given
    final JsonVelocityProcessorEmptyDecimal processor = new JsonVelocityProcessorEmptyDecimal(createVelocityDefinition(null, null));

    // When
    final var result = processor.valueFrom(null);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().value()).isEqualByComparingTo(BigDecimal.ONE);
  }

}