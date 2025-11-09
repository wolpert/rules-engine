package com.codeheadsystems.rules.factory;

import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValueType;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorDecimal;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorEmptyDecimal;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorEmptyInteger;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VelocityProcessorFactoryTest {

  private VelocityProcessorFactory factory;

  @BeforeEach
  void setUp() {
    factory = new VelocityProcessorFactory();
  }

  @Test
  void create_should_return_velocity_processor_empty_integer_when_type_is_integer_and_val_path_is_empty() {
    // Given
    VelocityDefinition definition = createVelocityDefinition(VelocityValueType.INTEGER, Optional.empty());

    // When
    VelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(VelocityProcessorEmptyInteger.class);
  }

  @Test
  void create_should_return_velocity_processor_empty_decimal_when_type_is_decimal_and_val_path_is_empty() {
    // Given
    VelocityDefinition definition = createVelocityDefinition(VelocityValueType.DECIMAL, Optional.empty());

    // When
    VelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(VelocityProcessorEmptyDecimal.class);
  }

  @Test
  void create_should_return_velocity_processor_integer_when_type_is_integer_and_val_path_is_present() {
    // Given
    VelocityDefinition definition = createVelocityDefinition(VelocityValueType.INTEGER, Optional.of("/amount"));

    // When
    VelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(VelocityProcessorInteger.class);
  }

  @Test
  void create_should_return_velocity_processor_decimal_when_type_is_decimal_and_val_path_is_present() {
    // Given
    VelocityDefinition definition = createVelocityDefinition(VelocityValueType.DECIMAL, Optional.of("/amount"));

    // When
    VelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(VelocityProcessorDecimal.class);
  }

  private VelocityDefinition createVelocityDefinition(VelocityValueType type, Optional<String> valPath) {
    VelocityDefinition definition = mock(VelocityDefinition.class);
    when(definition.type()).thenReturn(type);
    when(definition.valPath()).thenReturn(valPath);
    return definition;
  }
}