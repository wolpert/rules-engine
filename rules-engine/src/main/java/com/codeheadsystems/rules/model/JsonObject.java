package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The type Json object.
 */
public class JsonObject {

  private final JsonNode root;

  /**
   * Instantiates a new Json object.
   *
   * @param root the root
   */
  public JsonObject(JsonNode root) {
    this.root = root;
  }

  /**
   * As string string.
   *
   * @param path the path
   * @return the string
   */
  public String asString(String path) {
    final JsonNode node = root.at(path);
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
    final JsonNode node = root.at(path);
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
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isArray()) {
      return null;
    }
    JsonObject[] objects = new JsonObject[node.size()];
    for (int i = 0; i < node.size(); i++) {
      objects[i] = new JsonObject(node.get(i));
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
    final JsonNode node = root.at(path);
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
    final JsonNode node = root.at(path);
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
    final JsonNode node = root.at(path);
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
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isBoolean()) {
      return null;
    }
    return node.booleanValue();
  }

}
