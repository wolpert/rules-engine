package com.codeheadsystems.rules.dao;

import com.codeheadsystems.rules.converter.RuleConverter;
import com.codeheadsystems.rules.model.RuleIdentifier;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

/**
 * The type Dynamo db rules dao.
 */
@Singleton
public class DynamoDbRulesDao implements RulesDao {

  private final RuleConverter ruleConverter;
  private final DynamoDbClient dynamoDbClient;

  /**
   * Instantiates a new Dynamo db rules dao.
   */
  @Inject
  public DynamoDbRulesDao(final RuleConverter ruleConverter, final DynamoDbClient dynamoDbClient) {
    this.ruleConverter = ruleConverter;
    this.dynamoDbClient = dynamoDbClient;
  }

  @Override
  public List<RuleIdentifier> rulesFor(final RuleSetIdentifier ruleSetIdentifier) {
    final QueryRequest queryRequest = ruleConverter.queryRequestFrom(ruleSetIdentifier);
    final QueryResponse queryResponse = dynamoDbClient.query(queryRequest);
    return queryResponse.items().stream()
        .map(ruleConverter::toRuleIdentifier)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }
}
