package com.codeheadsystems.rules.dao;

import java.util.Map;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public enum ColumnNames {
  TENANT("tenant"),
  EVENT("event"),
  RULE_VERSION("rule_version"),
  EVENT_VERSION("event_version"),
  RULE_ID("rule_id"),
  ;

  private final String column;

  ColumnNames(final String column) {
    this.column = column;
  }

  public String column() {
    return column;
  }

  /**
   * Convenience method to get the attribute value from the map using this column name.
   * Does not check for null.
   *
   * @param attributes from DynamoDB.
   * @return an attribute value.
   */
  public AttributeValue get(Map<String, AttributeValue> attributes) {
    return attributes.get(column);
  }


}
