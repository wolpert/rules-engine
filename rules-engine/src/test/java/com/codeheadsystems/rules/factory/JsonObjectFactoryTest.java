package com.codeheadsystems.rules.factory;

import com.codeheadsystems.rules.exception.InvalidJsonException;
import com.codeheadsystems.rules.model.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class JsonObjectFactoryTest {

  private JsonObjectFactory factory;

  @BeforeEach
  void setUp() {
    ObjectMapper objectMapper = new ObjectMapper();
    factory = new JsonObjectFactory(objectMapper);
  }

  @Test
  void json_object_should_create_valid_json_object() {
    // Given
    String validJson = "{\"key\":\"value\",\"number\":123}";

    // When
    JsonObject result = factory.jsonObject(validJson);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.asString("/key")).isEqualTo("value");
    assertThat(result.asInteger("/number")).isEqualTo(123);
  }

  @Test
  void json_object_should_handle_empty_object() {
    // Given
    String emptyJson = "{}";

    // When
    JsonObject result = factory.jsonObject(emptyJson);

    // Then
    assertThat(result).isNotNull();
  }

  @Test
  void json_object_should_handle_nested_objects() {
    // Given
    String nestedJson = "{\"parent\":{\"child\":\"value\"}}";

    // When
    JsonObject result = factory.jsonObject(nestedJson);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.asString("/parent/child")).isEqualTo("value");
  }

  @Test
  void json_object_should_throw_invalid_json_exception_for_malformed_json() {
    // Given
    String malformedJson = "{\"key\":\"value\"";

    // When/Then
    assertThatThrownBy(() -> factory.jsonObject(malformedJson))
        .isInstanceOf(InvalidJsonException.class)
        .hasMessageContaining("Unable to parse json");
  }

  @Test
  void json_object_should_throw_exception_for_null_json() {
    // Given - null json

    // When/Then
    assertThatThrownBy(() -> factory.jsonObject(null))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void json_object_should_throw_invalid_json_exception_for_invalid_json() {
    // Given
    String invalidJson = "not json at all";

    // When/Then
    assertThatThrownBy(() -> factory.jsonObject(invalidJson))
        .isInstanceOf(InvalidJsonException.class)
        .hasMessageContaining("Unable to parse json");
  }
}