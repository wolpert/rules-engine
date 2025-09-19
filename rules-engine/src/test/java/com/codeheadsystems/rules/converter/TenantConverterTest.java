package com.codeheadsystems.rules.converter;

import com.codeheadsystems.rules.model.TableConfiguration;
import com.codeheadsystems.rules.model.Tenant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TenantConverterTest {

  private TableConfiguration tableConfiguration;
  private TenantConverter tenantConverter;

  @BeforeEach
  void setUp() {
    tableConfiguration = mock(TableConfiguration.class);
    when(tableConfiguration.hashKeyName()).thenReturn("hk");
    when(tableConfiguration.sortKeyName()).thenReturn("sk");
    when(tableConfiguration.typeColName()).thenReturn("type");
    when(tableConfiguration.tableName()).thenReturn("table");
    tenantConverter = new TenantConverter(tableConfiguration);
  }

  @Test
  void toHashKey_returnsExpectedValue() {
    assertThat(tenantConverter.toHashKey("foo")).isEqualTo("TENANT#foo");
  }

  @Test
  void toPutRequest_buildsCorrectRequest() {
    PutItemRequest request = tenantConverter.toPutRequest("foo");
    assertThat(request.tableName()).isEqualTo("table");
    assertThat(request.item()).containsEntry("hk", AttributeValue.fromS("TENANT#foo"));
    assertThat(request.item()).containsEntry("sk", AttributeValue.fromS("TENANT#"));
    assertThat(request.item()).containsEntry("type", AttributeValue.fromS("Tenant"));
    assertThat(request.item()).containsEntry("tenant_name", AttributeValue.fromS("foo"));
  }

  @Test
  void toGetRequest_buildsCorrectRequest() {
    GetItemRequest request = tenantConverter.toGetRequest("foo");
    assertThat(request.tableName()).isEqualTo("table");
    assertThat(request.key()).containsEntry("hk", AttributeValue.fromS("foo"));
    assertThat(request.key()).containsEntry("sk", AttributeValue.fromS("TENANT#"));
  }

  @Test
  void toTenant_returnsTenantWhenAttributesPresent() {
    Map<String, AttributeValue> attrs = Map.of("tenant_name", AttributeValue.fromS("foo"));
    Optional<Tenant> tenant = tenantConverter.toTenant(attrs);
    assertThat(tenant).isPresent();
    assertThat(tenant.get().name()).isEqualTo("foo");
  }

  @Test
  void toTenant_returnsEmptyWhenAttributesEmpty() {
    Optional<Tenant> tenant = tenantConverter.toTenant(Map.of());
    assertThat(tenant).isEmpty();
  }

  @Test
  void toDeleteRequest_buildsCorrectRequest() {
    DeleteItemRequest request = tenantConverter.toDeleteRequest("foo");
    assertThat(request.tableName()).isEqualTo("table");
    assertThat(request.key()).containsEntry("hk", AttributeValue.fromS("foo"));
    assertThat(request.key()).containsEntry("sk", AttributeValue.fromS("TENANT#"));
  }
}
