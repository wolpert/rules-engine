package com.codeheadsystems.rules.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class InvalidJsonExceptionTest {

  @Test
  void constructor_should_set_message_and_cause() {
    // Given
    String expectedMessage = "Invalid JSON format";
    JsonProcessingException cause = mock(JsonProcessingException.class);

    // When
    InvalidJsonException exception = new InvalidJsonException(expectedMessage, cause);

    // Then
    assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    assertThat(exception.getCause()).isEqualTo(cause);
  }

  @Test
  void constructor_should_handle_null_message() {
    // Given
    JsonProcessingException cause = mock(JsonProcessingException.class);

    // When
    InvalidJsonException exception = new InvalidJsonException(null, cause);

    // Then
    assertThat(exception.getMessage()).isNull();
    assertThat(exception.getCause()).isEqualTo(cause);
  }

  @Test
  void constructor_should_handle_null_cause() {
    // Given
    String expectedMessage = "Invalid JSON format";

    // When
    InvalidJsonException exception = new InvalidJsonException(expectedMessage, null);

    // Then
    assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void exception_should_be_instance_of_runtime_exception() {
    // Given
    String message = "Test message";
    JsonProcessingException cause = mock(JsonProcessingException.class);

    // When
    InvalidJsonException exception = new InvalidJsonException(message, cause);

    // Then
    assertThat(exception).isInstanceOf(RuntimeException.class);
  }
}