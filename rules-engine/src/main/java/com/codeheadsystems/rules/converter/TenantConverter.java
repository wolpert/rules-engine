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

@Singleton
public class TenantConverter {

  public static final String ROW_IDENTIFIER = "TENANT#";
  public static final String TYPE = Tenant.class.getSimpleName();
  public static final String COL_NAME = "tenant_name";

  private final TableConfiguration tableConfiguration;

  @Inject
  TenantConverter(final TableConfiguration tableConfiguration) {
    this.tableConfiguration = tableConfiguration;
  }

  public String toHashKey(final String tenantName) {
    return ROW_IDENTIFIER + tenantName;
  }


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

  public GetItemRequest toGetRequest(final String tenantName) {
    final ImmutableMap.Builder<String, AttributeValue> builder = ImmutableMap.builder();
    builder.put(tableConfiguration.hashKeyName(), fromS(tenantName));
    builder.put(tableConfiguration.sortKeyName(), fromS(ROW_IDENTIFIER));
    return GetItemRequest.builder()
        .tableName(tableConfiguration.tableName())
        .key(builder.build())
        .build();
  }

  public Optional<Tenant> toTenant(final Map<String, AttributeValue> attributes) {
    if (!attributes.isEmpty()) {
      return Optional.of(ImmutableTenant.builder()
          .name(attributes.get(COL_NAME).s())
          .build());
    }
    return Optional.empty();
  }

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
