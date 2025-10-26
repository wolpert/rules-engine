package com.codeheadsystems.rules.converter;

import static software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS;

import com.codeheadsystems.rules.dao.ColumnNames;
import com.codeheadsystems.rules.model.ImmutableRuleIdentifier;
import com.codeheadsystems.rules.model.Rule;
import com.codeheadsystems.rules.model.RuleIdentifier;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import com.codeheadsystems.rules.model.TableConfiguration;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.Version;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

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

  private static String hashKeyForRuleSetRule(final RuleSetIdentifier ruleSetIdentifier) {
    return String.format("%s%s:%s:%s",
        RULESET_RULE_IDENTIFIER,
        ruleSetIdentifier.tenant().value(),
        ruleSetIdentifier.event().value(),
        ruleSetIdentifier.eventVersion().value());
  }

  private static WriteRequest toPutRequest(Map<String, AttributeValue> item) {
    return WriteRequest.builder()
        .putRequest(PutRequest.builder().item(item).build())
        .build();
  }

  /**
   * Rules are stored for rulesets where the hash is the rulesetIdentifier, and the sort key is the rule identifier. The version
   * is a column on the item since you cannot have multiple versions for a ruleset.
   * So Hash="RULESET#tenant:eventType:version", Sort="RULE#ruleId", Version=1.0
   *
   * @param ruleSetIdentifier to query for rule identifiers.
   * @return query request.
   */
  public QueryRequest queryRequestFrom(RuleSetIdentifier ruleSetIdentifier) {
    String hashKey = hashKeyForRuleSetRule(ruleSetIdentifier);
    return QueryRequest.builder()
        .tableName(tableConfiguration.tableName())
        .keyConditionExpression(tableConfiguration.hashKeyName() + " = :" + tableConfiguration.hashKeyName())
        .expressionAttributeValues(Map.of(tableConfiguration.hashKeyName(), fromS(hashKey)))
        .build();
  }

  public PutItemRequest putItemRequestFrom(RuleSetIdentifier ruleSetIdentifier, RuleIdentifier ruleIdentifier) {
    Map<String, AttributeValue> item = toRow(ruleSetIdentifier, ruleIdentifier);
    return PutItemRequest.builder()
        .tableName(tableConfiguration.tableName())
        .returnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
        .item(item)
        .build();
  }

  public BatchWriteItemRequest batchWriteItemRequestFrom(Map<RuleSetIdentifier, RuleIdentifier> rules) {
    var requestBuilder = BatchWriteItemRequest.builder()
        .returnConsumedCapacity(ReturnConsumedCapacity.TOTAL);
    var putRequests = rules.entrySet().stream()
        .map(this::toRow)
        .map(RuleConverter::toPutRequest)
        .toList();
    requestBuilder.requestItems(Map.of(tableConfiguration.tableName(), putRequests));
    return requestBuilder.build();
  }

  /**
   * Used to map a single rule in a ruleset to a DynamoDB row.
   *
   * @param ruleSetIdentifier the set the rule is a member of.
   * @param ruleIdentifier    the rule itself.
   * @return a map of attributes for DynamoDB.
   */
  public Map<String, AttributeValue> toRow(RuleSetIdentifier ruleSetIdentifier, RuleIdentifier ruleIdentifier) {
    return Map.of(
        tableConfiguration.hashKeyName(), fromS(hashKeyForRuleSetRule(ruleSetIdentifier)),
        tableConfiguration.sortKeyName(), fromS(RULE_IDENTIFIER + ruleIdentifier.id()),
        ColumnNames.TENANT.column(), fromS(ruleSetIdentifier.tenant().value()),
        ColumnNames.RULE_ID.column(), fromS(ruleIdentifier.id()),
        ColumnNames.RULE_VERSION.column(), fromS(ruleIdentifier.version().value()),
        ColumnNames.EVENT_VERSION.column(), fromS(ruleSetIdentifier.eventVersion().value()),
        ColumnNames.EVENT.column(), fromS(ruleSetIdentifier.event().value())
    );
  }

  public Optional<RuleIdentifier> toRuleIdentifier(Map<String, AttributeValue> attributes) {
    if (!attributes.isEmpty()) {
      return Optional.of(
          ImmutableRuleIdentifier.builder()
              .tenant(Tenant.of(ColumnNames.TENANT.get(attributes).s()))
              .id(ColumnNames.RULE_ID.get(attributes).s())
              .version(Version.of(ColumnNames.RULE_VERSION.get(attributes).s()))
              .build()
      );
    }
    return Optional.empty();
  }

  private Map<String, AttributeValue> toRow(Map.Entry<RuleSetIdentifier, RuleIdentifier> entry) {
    return toRow(entry.getKey(), entry.getValue());
  }
}
