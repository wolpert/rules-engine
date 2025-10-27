package com.codeheadsystems.rules.dao;

import java.util.Map;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * The enum Column names.
 */
public enum ColumnNames {
  /**
   * Tenant column names.
   */
  TENANT("tenant"),
  /**
   * Event column names.
   */
  EVENT("event"),
  /**
   * Rule version column names.
   */
  RULE_VERSION("rule_version"),
  /**
   * Event version column names.
   */
  EVENT_VERSION("event_version"),
  /**
   * Rule id column names.
   */
  RULE_ID("rule_id"),
  ;

  private final String column;

  ColumnNames(final String column) {
    this.column = column;
  }

  /**
   * Column string.
   *
   * @return the string
   */
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
