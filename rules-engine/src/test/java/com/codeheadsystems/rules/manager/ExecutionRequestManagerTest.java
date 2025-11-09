package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.converter.FactsConverter;
import com.codeheadsystems.rules.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExecutionRequestManagerTest {

  private ExecutionRequestManager executionRequestManager;

  @Mock
  private FactsConverter factsConverter;

  @Mock
  private Clock clock;

  private ExecutionEnvironment executionEnvironment;

  @BeforeEach
  void setUp() {
    executionEnvironment = ImmutableExecutionEnvironment.builder().value("test").build();
    executionRequestManager = new ExecutionRequestManager(factsConverter, executionEnvironment, clock);
  }

  @Test
  void create_should_create_execution_request_with_all_fields() {
    // Given
    Tenant tenant = ImmutableTenant.builder().value("testTenant").build();
    EventType eventType = ImmutableEventType.builder().value("testEvent").build();
    Version version = ImmutableVersion.builder().value("1.0").build();
    EventIdentifier eventId = ImmutableEventIdentifier.builder().value("event123").build();
    String jsonEventData = "{\"key\":\"value\"}";
    Instant fixedInstant = Instant.parse("2023-01-01T00:00:00Z");

    @SuppressWarnings("unchecked")
    Facts<JsonObject> mockFacts = mock(Facts.class);

    when(factsConverter.convert(eventId.value(), jsonEventData)).thenReturn(mockFacts);
    when(clock.instant()).thenReturn(fixedInstant);

    // When
    ExecutionRequest<JsonObject> result = executionRequestManager.create(tenant, eventType, version, eventId, jsonEventData);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.executionEnvironment()).isEqualTo(executionEnvironment);
    assertThat(result.facts()).isEqualTo(mockFacts);
    assertThat(result.eventIdentifier()).isEqualTo(eventId);
    assertThat(result.requestTimestamp()).isEqualTo(fixedInstant);

    RuleSetIdentifier expectedRuleSetIdentifier = RuleSetIdentifier.of(tenant, eventType, version);
    assertThat(result.ruleSetIdentifier()).isEqualTo(expectedRuleSetIdentifier);

    verify(factsConverter).convert(eventId.value(), jsonEventData);
    verify(clock).instant();
  }

  @Test
  void create_should_call_facts_converter_with_correct_parameters() {
    // Given
    Tenant tenant = ImmutableTenant.builder().value("testTenant").build();
    EventType eventType = ImmutableEventType.builder().value("testEvent").build();
    Version version = ImmutableVersion.builder().value("1.0").build();
    EventIdentifier eventId = ImmutableEventIdentifier.builder().value("event123").build();
    String jsonEventData = "{\"test\":\"data\"}";
    Instant fixedInstant = Instant.now();

    @SuppressWarnings("unchecked")
    Facts<JsonObject> mockFacts = mock(Facts.class);

    when(factsConverter.convert(anyString(), anyString())).thenReturn(mockFacts);
    when(clock.instant()).thenReturn(fixedInstant);

    // When
    executionRequestManager.create(tenant, eventType, version, eventId, jsonEventData);

    // Then
    verify(factsConverter).convert(eventId.value(), jsonEventData);
  }

  @Test
  void create_should_use_current_time_from_clock() {
    // Given
    Tenant tenant = ImmutableTenant.builder().value("testTenant").build();
    EventType eventType = ImmutableEventType.builder().value("testEvent").build();
    Version version = ImmutableVersion.builder().value("1.0").build();
    EventIdentifier eventId = ImmutableEventIdentifier.builder().value("event123").build();
    String jsonEventData = "{}";
    Instant expectedTime = Instant.parse("2023-06-15T10:30:00Z");

    @SuppressWarnings("unchecked")
    Facts<JsonObject> mockFacts = mock(Facts.class);

    when(factsConverter.convert(anyString(), anyString())).thenReturn(mockFacts);
    when(clock.instant()).thenReturn(expectedTime);

    // When
    ExecutionRequest<JsonObject> result = executionRequestManager.create(tenant, eventType, version, eventId, jsonEventData);

    // Then
    assertThat(result.requestTimestamp()).isEqualTo(expectedTime);
    verify(clock).instant();
  }
}