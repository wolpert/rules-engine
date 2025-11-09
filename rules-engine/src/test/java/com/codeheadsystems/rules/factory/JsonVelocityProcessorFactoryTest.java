package com.codeheadsystems.rules.factory;

import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValueType;
import com.codeheadsystems.rules.processor.JsonVelocityProcessor;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorDecimal;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorEmptyDecimal;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorEmptyInteger;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonVelocityProcessorFactoryTest {

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
    JsonVelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(JsonVelocityProcessorEmptyInteger.class);
  }

  @Test
  void create_should_return_velocity_processor_empty_decimal_when_type_is_decimal_and_val_path_is_empty() {
    // Given
    VelocityDefinition definition = createVelocityDefinition(VelocityValueType.DECIMAL, Optional.empty());

    // When
    JsonVelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(JsonVelocityProcessorEmptyDecimal.class);
  }

  @Test
  void create_should_return_velocity_processor_integer_when_type_is_integer_and_val_path_is_present() {
    // Given
    VelocityDefinition definition = createVelocityDefinition(VelocityValueType.INTEGER, Optional.of("/amount"));

    // When
    JsonVelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(JsonVelocityProcessorInteger.class);
  }

  @Test
  void create_should_return_velocity_processor_decimal_when_type_is_decimal_and_val_path_is_present() {
    // Given
    VelocityDefinition definition = createVelocityDefinition(VelocityValueType.DECIMAL, Optional.of("/amount"));

    // When
    JsonVelocityProcessor<?> processor = factory.create(definition);

    // Then
    assertThat(processor).isInstanceOf(JsonVelocityProcessorDecimal.class);
  }

  private VelocityDefinition createVelocityDefinition(VelocityValueType type, Optional<String> valPath) {
    VelocityDefinition definition = mock(VelocityDefinition.class);
    when(definition.type()).thenReturn(type);
    when(definition.valPath()).thenReturn(valPath);
    return definition;
  }
}