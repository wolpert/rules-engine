package com.codeheadsystems.rules.converter;

import com.codeheadsystems.rules.factory.JsonObjectFactory;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableFacts;
import com.codeheadsystems.rules.model.JsonObject;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Facts converter.
 */
@Singleton
public class FactsConverter {

  private final JsonObjectFactory jsonObjectFactory;

  /**
   * Instantiates a new Facts converter.
   *
   * @param jsonObjectFactory the json object factory
   */
  @Inject
  public FactsConverter(final JsonObjectFactory jsonObjectFactory) {
    this.jsonObjectFactory = jsonObjectFactory;
  }

  /**
   * Convert facts.
   *
   * @param eventId the event id
   * @param payload the payload
   * @return the facts
   */
  public Facts convert(final String eventId, final String payload) {
    final JsonObject jsonObject = jsonObjectFactory.jsonObject(payload);
    return ImmutableFacts.builder()
        .eventId(eventId)
        .addJsonObjects(jsonObject)
        .build();
  }

}
