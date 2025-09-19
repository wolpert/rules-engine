package com.codeheadsystems.rules.converter;

import com.codeheadsystems.rules.factory.JsonObjectFactory;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FactsConverterTest {

  private JsonObjectFactory jsonObjectFactory;
  private FactsConverter factsConverter;

  @BeforeEach
  void setUp() {
    jsonObjectFactory = mock(JsonObjectFactory.class);
    factsConverter = new FactsConverter(jsonObjectFactory);
  }

  @Test
  void convert_shouldReturnFactsWithCorrectEventIdAndJsonObject() {
    String eventId = "event-123";
    String payload = "{\"key\":\"value\"}";
    JsonObject jsonObject = mock(JsonObject.class);

    when(jsonObjectFactory.jsonObject(payload)).thenReturn(jsonObject);

    Facts facts = factsConverter.convert(eventId, payload);

    assertThat(facts).isNotNull();
    assertThat(facts.eventId()).isEqualTo(eventId);
    assertThat(facts.jsonObjects()).containsExactly(jsonObject);
  }
}
