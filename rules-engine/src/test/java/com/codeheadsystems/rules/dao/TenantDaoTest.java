package com.codeheadsystems.rules.dao;

import com.codeheadsystems.rules.converter.TenantConverter;
import com.codeheadsystems.rules.model.Tenant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantDaoTest {

  @Mock
  private TenantConverter tenantConverter;
  @Mock
  private DynamoDbClient dynamoDbClient;
  @InjectMocks
  private TenantDao tenantDao;

  @Test
  void getTenant_returnsTenantWhenFound() {
    String tenantName = "foo";
    GetItemRequest request = mock(GetItemRequest.class);
    GetItemResponse response = mock(GetItemResponse.class);
    Map<String, AttributeValue> item = Map.of("tenant_name", AttributeValue.fromS(tenantName));
    Tenant tenant = Tenant.of(tenantName);

    when(tenantConverter.toGetRequest(tenantName)).thenReturn(request);
    when(dynamoDbClient.getItem(request)).thenReturn(response);
    when(response.hasItem()).thenReturn(true);
    when(response.item()).thenReturn(item);
    when(tenantConverter.toTenant(item)).thenReturn(Optional.of(tenant));

    Optional<Tenant> result = tenantDao.getTenant(tenantName);

    assertThat(result).isPresent();
    assertThat(result.get().value()).isEqualTo(tenantName);
  }

  @Test
  void getTenant_returnsEmptyWhenNotFound() {
    String tenantName = "bar";
    GetItemRequest request = mock(GetItemRequest.class);
    GetItemResponse response = mock(GetItemResponse.class);

    when(tenantConverter.toGetRequest(tenantName)).thenReturn(request);
    when(dynamoDbClient.getItem(request)).thenReturn(response);
    when(response.hasItem()).thenReturn(false);

    Optional<Tenant> result = tenantDao.getTenant(tenantName);

    assertThat(result).isEmpty();
  }

  @Test
  void create_returnsTenantWhenAttributesPresent() {
    String tenantName = "baz";
    PutItemRequest request = mock(PutItemRequest.class);
    PutItemResponse response = mock(PutItemResponse.class);
    Map<String, AttributeValue> attrs = Map.of("tenant_name", AttributeValue.fromS(tenantName));
    Tenant tenant = Tenant.of(tenantName);

    when(tenantConverter.toPutRequest(tenantName)).thenReturn(request);
    when(dynamoDbClient.putItem(request)).thenReturn(response);
    when(response.hasAttributes()).thenReturn(true);
    when(response.attributes()).thenReturn(attrs);
    when(tenantConverter.toTenant(attrs)).thenReturn(Optional.of(tenant));

    Tenant result = tenantDao.create(tenantName);

    assertThat(result).isNotNull();
    assertThat(result.value()).isEqualTo(tenantName);
  }

  @Test
  void create_throwsExceptionWhenNoAttributes() {
    String tenantName = "baz";
    PutItemRequest request = mock(PutItemRequest.class);
    PutItemResponse response = mock(PutItemResponse.class);

    when(tenantConverter.toPutRequest(tenantName)).thenReturn(request);
    when(dynamoDbClient.putItem(request)).thenReturn(response);
    when(response.hasAttributes()).thenReturn(false);

    assertThatThrownBy(() -> tenantDao.create(tenantName))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(tenantName);
  }

  @Test
  void delete_returnsTrueWhenHasAttributes() {
    Tenant tenant = Tenant.of("foo");
    DeleteItemRequest request = mock(DeleteItemRequest.class);
    DeleteItemResponse response = mock(DeleteItemResponse.class);

    when(tenantConverter.toDeleteRequest(tenant.value())).thenReturn(request);
    when(dynamoDbClient.deleteItem(request)).thenReturn(response);
    when(response.hasAttributes()).thenReturn(true);

    boolean result = tenantDao.delete(tenant);

    assertThat(result).isTrue();
  }

  @Test
  void delete_returnsFalseWhenNoAttributes() {
    Tenant tenant = Tenant.of("foo");
    DeleteItemRequest request = mock(DeleteItemRequest.class);
    DeleteItemResponse response = mock(DeleteItemResponse.class);

    when(tenantConverter.toDeleteRequest(tenant.value())).thenReturn(request);
    when(dynamoDbClient.deleteItem(request)).thenReturn(response);
    when(response.hasAttributes()).thenReturn(false);

    boolean result = tenantDao.delete(tenant);

    assertThat(result).isFalse();
  }
}
