package com.codeheadsystems.rules.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ObjectMapperFactoryTest {

  private ObjectMapperFactory factory;

  @BeforeEach
  void setUp() {
    factory = new ObjectMapperFactory();
  }

  @Test
  void object_mapper_should_return_configured_object_mapper() {
    // Given - factory is set up in setUp()

    // When
    ObjectMapper mapper = factory.objectMapper();

    // Then
    assertThat(mapper).isNotNull();
    assertThat(mapper.getRegisteredModuleIds()).contains(Jdk8Module.class.getName());
  }

  @Test
  void object_mapper_should_return_new_instance_each_time() {
    // Given - factory is set up in setUp()

    // When
    ObjectMapper mapper1 = factory.objectMapper();
    ObjectMapper mapper2 = factory.objectMapper();

    // Then
    assertThat(mapper1).isNotSameAs(mapper2);
  }
}