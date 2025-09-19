package com.codeheadsystems.rules.factory;

import static com.github.benmanes.caffeine.cache.Caffeine.*;

import com.codeheadsystems.rules.exception.InvalidJsonException;
import com.codeheadsystems.rules.model.JsonObject;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.LoadingCache;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Json object factory.
 */
@Singleton
public class JsonObjectFactory {

  private final ObjectMapper objectMapper;

  private final LoadingCache<String, JsonPointer> cache;
  /**
   * Instantiates a new Json object factory.
   *
   * @param objectMapper the object mapper
   */
  @Inject
  public JsonObjectFactory(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.cache = newBuilder().maximumSize(2000).build(JsonPointer::compile);
  }

  /**
   * Json object json object.
   *
   * @param json the json
   * @return the json object
   */
  public JsonObject jsonObject(final String json) {
    try {
      return new JsonObject(cache, objectMapper.readValue(json, ObjectNode.class));
    } catch (JsonProcessingException e) {
      throw new InvalidJsonException("Unable to parse json: " + json, e);
    }
  }
}
