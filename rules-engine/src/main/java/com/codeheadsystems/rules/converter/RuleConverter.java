package com.codeheadsystems.rules.converter;

import static software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS;

import com.codeheadsystems.rules.model.Rule;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import com.codeheadsystems.rules.model.TableConfiguration;
import com.google.common.collect.ImmutableMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

@Singleton
public class RuleConverter {

  /**
   * The constant ROW_IDENTIFIER.
   */
  public static final String RULESET_RULE_IDENTIFIER = "RULESET_RULE#";
  public static final String RULE_IDENTIFIER = "RULE#";
  /**
   * The constant TYPE.
   */
  public static final String TYPE = Rule.class.getSimpleName();
  /**
   * The constant COL_NAME.
   */
  public static final String COL_NAME = "rule_id";

  private final TableConfiguration tableConfiguration;

  @Inject
  public RuleConverter(final TableConfiguration tableConfiguration) {
    this.tableConfiguration = tableConfiguration;
  }

  /**
   * Rules are stored for rulesets where the hash is the rulesetIdentifier, and the sort key is the rule identifier. The version
   * is a column on the item since you cannot have multiple versions for a ruleset.
   * So Hash="RULESET#tenant:eventType:version", Sort="RULE#ruleId", Version=1.0
   * @param ruleSetIdentifier to query for rule identifiers.
   * @return query request.
   */
  public QueryRequest queryRequestFrom(RuleSetIdentifier ruleSetIdentifier) {
    final ImmutableMap.Builder<String, AttributeValue> builder = ImmutableMap.builder();
    String hashKey = hashKeyForRuleSetRule(ruleSetIdentifier);
    builder.put(tableConfiguration.hashKeyName(), fromS(hashKey));
    return QueryRequest.builder()
        .tableName(tableConfiguration.tableName())
        .keyConditionExpression(tableConfiguration.hashKeyName() + " = :" + tableConfiguration.hashKeyName())
        .expressionAttributeValues(builder.build())
        .build();
  }

  private static String hashKeyForRuleSetRule(final RuleSetIdentifier ruleSetIdentifier) {
    return String.format("%s%s:%s:%s",
        RULESET_RULE_IDENTIFIER,
        ruleSetIdentifier.tenant().value(),
        ruleSetIdentifier.eventType().value(),
        ruleSetIdentifier.eventVersion().value());
  }

}
