package com.codeheadsystems.rules.manager;


import com.codeheadsystems.rules.model.TableConfiguration;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.BillingMode;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.dynamodb.model.TimeToLiveSpecification;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest;

/**
 * Used for programmatic management of the table.
 * Note, in production, you should use CDK to manage instead of this. This class
 * was built mostly for testing purposes. Remember to encrypt the table with keys
 * as required by your organization.
 */
@Singleton
public class AwsManager {

  private final DynamoDbClient client;
  private final TableConfiguration tableConfiguration;

  /**
   * Instantiates a new Aws manager.
   *
   * @param client             the client
   * @param tableConfiguration the table configuration
   */
  @Inject
  public AwsManager(final DynamoDbClient client,
                    final TableConfiguration tableConfiguration) {
    this.client = client;
    this.tableConfiguration = tableConfiguration;
  }

  /**
   * Create table request create table request.
   *
   * @return the create table request
   */
  protected CreateTableRequest createTableRequest() {
    final KeySchemaElement hashKey = KeySchemaElement.builder()
        .keyType(KeyType.HASH).attributeName(tableConfiguration.hashKeyName()).build();
    final KeySchemaElement rangeKey = KeySchemaElement.builder()
        .keyType(KeyType.RANGE).attributeName(tableConfiguration.sortKeyName()).build();
    final List<AttributeDefinition> attributeDefinitions = List.of(
        AttributeDefinition.builder()
            .attributeName(tableConfiguration.hashKeyName()).attributeType(ScalarAttributeType.S).build(),
        AttributeDefinition.builder()
            .attributeName(tableConfiguration.sortKeyName()).attributeType(ScalarAttributeType.S).build()
    );

    return CreateTableRequest.builder()
        .tableName(tableConfiguration.tableName())
        .billingMode(BillingMode.PAY_PER_REQUEST)
        .keySchema(hashKey, rangeKey)
        .attributeDefinitions(attributeDefinitions)
        .build();
  }


  /**
   * Create table.
   */
  public void createTable() {
    client.createTable(createTableRequest());
    client.updateTimeToLive(updateTimeToLiveRequest());
  }

  /**
   * Update time to live request update time to live request.
   *
   * @return the update time to live request
   */
  protected UpdateTimeToLiveRequest updateTimeToLiveRequest() {
    return UpdateTimeToLiveRequest.builder()
        .tableName(tableConfiguration.tableName())
        .timeToLiveSpecification(TimeToLiveSpecification.builder()
            .attributeName(tableConfiguration.ttlColName()).enabled(true).build())
        .build();
  }

}
