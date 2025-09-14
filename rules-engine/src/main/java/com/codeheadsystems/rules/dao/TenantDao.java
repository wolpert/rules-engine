package com.codeheadsystems.rules.dao;

import com.codeheadsystems.metrics.declarative.Metrics;
import com.codeheadsystems.rules.converter.TenantConverter;
import com.codeheadsystems.rules.model.Tenant;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Singleton
public class TenantDao {

  private final TenantConverter tenantConverter;
  private final DynamoDbClient dynamoDbClient;

  @Inject
  public TenantDao(final TenantConverter tenantConverter, final DynamoDbClient dynamoDbClient) {
    this.tenantConverter = tenantConverter;
    this.dynamoDbClient = dynamoDbClient;
  }

  @Metrics
  public Optional<Tenant> getTenant(String tenantName) {
    return Optional.empty();
  }

  @Metrics
  public Tenant create(String tenantName) {
    return null;
  }

  @Metrics
  public boolean delete(Tenant tenant) {
    return false;
  }


}
