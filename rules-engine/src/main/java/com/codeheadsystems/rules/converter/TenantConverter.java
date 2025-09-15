package com.codeheadsystems.rules.converter;

import static software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS;

import com.codeheadsystems.rules.model.ImmutableTenant;
import com.codeheadsystems.rules.model.TableConfiguration;
import com.codeheadsystems.rules.model.Tenant;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;

/**
 * The type Tenant converter.
 */
@Singleton
public class TenantConverter {

  /**
   * The constant ROW_IDENTIFIER.
   */
  public static final String ROW_IDENTIFIER = "TENANT#";
  /**
   * The constant TYPE.
   */
  public static final String TYPE = Tenant.class.getSimpleName();
  /**
   * The constant COL_NAME.
   */
  public static final String COL_NAME = "tenant_name";

  private final TableConfiguration tableConfiguration;

  /**
   * Instantiates a new Tenant converter.
   *
   * @param tableConfiguration the table configuration
   */
  @Inject
  TenantConverter(final TableConfiguration tableConfiguration) {
    this.tableConfiguration = tableConfiguration;
  }

  /**
   * To hash key string.
   *
   * @param tenantName the tenant name
   * @return the string
   */
  public String toHashKey(final String tenantName) {
    return ROW_IDENTIFIER + tenantName;
  }


  /**
   * To put request put item request.
   *
   * @param tenantName the tenant name
   * @return the put item request
   */
  public PutItemRequest toPutRequest(final String tenantName) {
    final ImmutableMap.Builder<String, AttributeValue> builder = ImmutableMap.builder();
    builder.put(tableConfiguration.hashKeyName(), fromS(toHashKey(tenantName)));
    builder.put(tableConfiguration.sortKeyName(), fromS(ROW_IDENTIFIER));
    builder.put(tableConfiguration.typeColName(), fromS(TYPE));
    builder.put(COL_NAME, fromS(tenantName));
    return PutItemRequest.builder()
        .tableName(tableConfiguration.tableName())
        .returnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
        .item(builder.build())
        .build();
  }

  /**
   * To get request get item request.
   *
   * @param tenantName the tenant name
   * @return the get item request
   */
  public GetItemRequest toGetRequest(final String tenantName) {
    final ImmutableMap.Builder<String, AttributeValue> builder = ImmutableMap.builder();
    builder.put(tableConfiguration.hashKeyName(), fromS(tenantName));
    builder.put(tableConfiguration.sortKeyName(), fromS(ROW_IDENTIFIER));
    return GetItemRequest.builder()
        .tableName(tableConfiguration.tableName())
        .key(builder.build())
        .build();
  }

  /**
   * To tenant optional.
   *
   * @param attributes the attributes
   * @return the optional
   */
  public Optional<Tenant> toTenant(final Map<String, AttributeValue> attributes) {
    if (!attributes.isEmpty()) {
      return Optional.of(ImmutableTenant.builder()
          .name(attributes.get(COL_NAME).s())
          .build());
    }
    return Optional.empty();
  }

  /**
   * To delete request delete item request.
   *
   * @param tenantName the tenant name
   * @return the delete item request
   */
  public DeleteItemRequest toDeleteRequest(final String tenantName) {
    final ImmutableMap.Builder<String, AttributeValue> builder = ImmutableMap.builder();
    builder.put(tableConfiguration.hashKeyName(), fromS(tenantName));
    builder.put(tableConfiguration.sortKeyName(), fromS(ROW_IDENTIFIER));
    return DeleteItemRequest.builder()
        .tableName(tableConfiguration.tableName())
        .key(builder.build())
        .build();
  }

}
