package com.codeheadsystems.rules.factory;

import com.codeheadsystems.rules.exception.InvalidJsonException;
import com.codeheadsystems.rules.model.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Json object factory.
 */
@Singleton
public class JsonObjectFactory {

  private final ObjectMapper objectMapper;

  /**
   * Instantiates a new Json object factory.
   *
   * @param objectMapper the object mapper
   */
  @Inject
  public JsonObjectFactory(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  /**
   * Json object json object.
   *
   * @param json the json
   * @return the json object
   */
  public JsonObject jsonObject(final String json) {
    try {
      return new JsonObject(objectMapper.readValue(json, ObjectNode.class));
    } catch (JsonProcessingException e) {
      throw new InvalidJsonException("Unable to parse json: " + json, e);
    }
  }
}
