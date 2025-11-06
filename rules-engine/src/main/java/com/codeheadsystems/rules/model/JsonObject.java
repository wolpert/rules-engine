package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Json object. Treat this as immutable.
 * <p>
 * TODO: cache json pointers at the event or tenant level. This will compile the json path each time.
 */
public class JsonObject {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonObject.class);

  private final LoadingCache<String, JsonNode> cache;
  private final LoadingCache<String, JsonPointer> pointerCache;

  /**
   * Instantiates a new Json object.
   *
   * @param jsonPointerCache that is shared.
   * @param root             the root
   */
  public JsonObject(final LoadingCache<String, JsonPointer> jsonPointerCache, JsonNode root) {
    this.pointerCache = jsonPointerCache;
    this.cache = Caffeine.newBuilder().build(jsonPtrExpr -> {
      JsonPointer jsonPointer = jsonPointerCache.get(jsonPtrExpr);
      if (jsonPointer == null) {
        LOGGER.warn("Unable to compile json pointer: {}", jsonPtrExpr);
        return MissingNode.getInstance();
      }
      return root.at(jsonPointer);
    });
  }

  /**
   * Exists boolean.
   *
   * @param path the path
   * @return the boolean
   */
  public boolean exists(String path) {
    final JsonNode node = cache.get(path);
    return !node.isMissingNode();
  }

  /**
   * As string string.
   *
   * @param path the path
   * @return the string
   */
  public String asString(String path) {
    final JsonNode node = cache.get(path);
    if (node.isMissingNode() || !node.isTextual()) {
      return null;
    }
    return node.asText();
  }

  /**
   * As integer integer.
   *
   * @param path the path
   * @return the integer
   */
  public Integer asInteger(String path) {
    final JsonNode node = cache.get(path);
    if (node.isMissingNode() || !node.isInt()) {
      return null;
    }
    return node.asInt();
  }

  /**
   * As array json object [ ].
   *
   * @param path the path
   * @return the json object [ ]
   */
  public JsonObject[] asArray(String path) {
    final JsonNode node = cache.get(path);
    if (node.isMissingNode() || !node.isArray()) {
      return null;
    }
    JsonObject[] objects = new JsonObject[node.size()];
    for (int i = 0; i < node.size(); i++) {
      objects[i] = new JsonObject(pointerCache, node.get(i));
    }
    return objects;
  }

  /**
   * As double double.
   *
   * @param path the path
   * @return the double
   */
  public Double asDouble(String path) {
    final JsonNode node = cache.get(path);
    if (node.isMissingNode() || !node.isDouble()) {
      return null;
    }
    return node.doubleValue();
  }

  /**
   * As big decimal big decimal.
   *
   * @param path the path
   * @return the big decimal
   */
  public BigDecimal asBigDecimal(String path) {
    final JsonNode node = cache.get(path);
    if (node.isMissingNode() || !node.isNumber()) {
      return null;
    }
    return node.decimalValue();
  }

  /**
   * As big integer big integer.
   *
   * @param path the path
   * @return the big integer
   */
  public BigInteger asBigInteger(String path) {
    final JsonNode node = cache.get(path);
    if (node.isMissingNode() || !node.isNumber()) {
      return null;
    }
    return node.bigIntegerValue();
  }

  /**
   * As boolean boolean.
   *
   * @param path the path
   * @return the boolean
   */
  public Boolean asBoolean(String path) {
    final JsonNode node = cache.get(path);
    if (node.isMissingNode() || !node.isBoolean()) {
      return null;
    }
    return node.booleanValue();
  }

}
