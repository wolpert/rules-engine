package com.codeheadsystems.rules.dao;

import com.codeheadsystems.metrics.declarative.Metrics;
import com.codeheadsystems.rules.converter.TenantConverter;
import com.codeheadsystems.rules.model.Tenant;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

/**
 * The type Tenant dao.
 */
@Singleton
public class TenantDao {

  private final TenantConverter tenantConverter;
  private final DynamoDbClient dynamoDbClient;

  /**
   * Instantiates a new Tenant dao.
   *
   * @param tenantConverter the tenant converter
   * @param dynamoDbClient  the dynamo db client
   */
  @Inject
  public TenantDao(final TenantConverter tenantConverter, final DynamoDbClient dynamoDbClient) {
    this.tenantConverter = tenantConverter;
    this.dynamoDbClient = dynamoDbClient;
  }

  /**
   * Gets tenant.
   *
   * @param tenantName the tenant name
   * @return the tenant
   */
  @Metrics
  public Optional<Tenant> getTenant(String tenantName) {
    final GetItemRequest getItemRequest = tenantConverter.toGetRequest(tenantName);
    final GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
    if (getItemResponse.hasItem()) {
      return tenantConverter.toTenant(getItemResponse.item());
    } else {
      return Optional.empty();
    }
  }

  /**
   * Create tenant.
   *
   * @param tenantName the tenant name
   * @return the tenant
   */
  @Metrics
  public Tenant create(String tenantName) {
    final PutItemRequest putItemRequest = tenantConverter.toPutRequest(tenantName);
    final PutItemResponse putItemResponse = dynamoDbClient.putItem(putItemRequest);
    if (putItemResponse.hasAttributes()) {
      return tenantConverter.toTenant(putItemResponse.attributes()).orElse(null);
    }
    throw new IllegalArgumentException("Unable to create tenant: " + tenantName);
  }

  /**
   * Delete boolean.
   *
   * @param tenant the tenant
   * @return the boolean
   */
  @Metrics
  public boolean delete(Tenant tenant) {
    final DeleteItemRequest deleteItemRequest = tenantConverter.toDeleteRequest(tenant.name());
    final DeleteItemResponse deleteItemResponse = dynamoDbClient.deleteItem(deleteItemRequest);
    return deleteItemResponse.hasAttributes();
  }


}
