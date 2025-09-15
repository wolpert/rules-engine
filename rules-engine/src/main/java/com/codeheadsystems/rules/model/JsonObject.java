package com.codeheadsystems.rules.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonObject {

  private final JsonNode root;

  public JsonObject(JsonNode root) {
    this.root = root;
  }

  public String asString(String path) {
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isTextual()) {
      return null;
    }
    return node.asText();
  }

  public Integer asInteger(String path) {
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isInt()) {
      return null;
    }
    return node.asInt();
  }

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

  public Double asDouble(String path) {
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isDouble()) {
      return null;
    }
    return node.doubleValue();
  }

  public BigDecimal asBigDecimal(String path) {
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isNumber()) {
      return null;
    }
    return node.decimalValue();
  }

  public BigInteger asBigInteger(String path) {
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isNumber()) {
      return null;
    }
    return node.bigIntegerValue();
  }

  public Boolean asBoolean(String path) {
    final JsonNode node = root.at(path);
    if (node.isMissingNode() || !node.isBoolean()) {
      return null;
    }
    return node.booleanValue();
  }

}
