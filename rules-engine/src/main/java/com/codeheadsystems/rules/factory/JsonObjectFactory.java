package com.codeheadsystems.rules.factory;

import com.codeheadsystems.rules.model.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonObjectFactory {

  private final ObjectMapper objectMapper;

  @Inject
  public JsonObjectFactory(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public JsonObject jsonObject(final String json) {
    try {
      return new JsonObject(objectMapper.readValue(json, ObjectNode.class));
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Unable to parse json: " + json, e);
    }
  }
}
