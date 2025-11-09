package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.dao.TenantDao;
import com.codeheadsystems.rules.model.ImmutableTenant;
import com.codeheadsystems.rules.model.Tenant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantManagerTest {

  private TenantManager tenantManager;

  @Mock
  private TenantDao tenantDao;

  @BeforeEach
  void setUp() {
    tenantManager = new TenantManager(tenantDao);
  }

  @Test
  void get_or_create_tenant_when_tenant_exists_should_return_existing_tenant() {
    // Given
    String tenantName = "existingTenant";
    Tenant existingTenant = ImmutableTenant.builder().value(tenantName).build();
    when(tenantDao.getTenant(tenantName)).thenReturn(Optional.of(existingTenant));

    // When
    Tenant result = tenantManager.getOrCreateTenant(tenantName);

    // Then
    assertThat(result).isEqualTo(existingTenant);
    verify(tenantDao).getTenant(tenantName);
    verify(tenantDao, never()).create(tenantName);
  }

  @Test
  void get_or_create_tenant_when_tenant_does_not_exist_should_create_and_return_new_tenant() {
    // Given
    String tenantName = "newTenant";
    Tenant createdTenant = ImmutableTenant.builder().value(tenantName).build();
    when(tenantDao.getTenant(tenantName)).thenReturn(Optional.empty());
    when(tenantDao.create(tenantName)).thenReturn(createdTenant);

    // When
    Tenant result = tenantManager.getOrCreateTenant(tenantName);

    // Then
    assertThat(result).isEqualTo(createdTenant);
    verify(tenantDao).getTenant(tenantName);
    verify(tenantDao).create(tenantName);
  }

  @Test
  void get_tenant_when_tenant_exists_should_return_optional_with_tenant() {
    // Given
    String tenantName = "existingTenant";
    Tenant existingTenant = ImmutableTenant.builder().value(tenantName).build();
    when(tenantDao.getTenant(tenantName)).thenReturn(Optional.of(existingTenant));

    // When
    Optional<Tenant> result = tenantManager.getTenant(tenantName);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(existingTenant);
    verify(tenantDao).getTenant(tenantName);
  }

  @Test
  void get_tenant_when_tenant_does_not_exist_should_return_empty_optional() {
    // Given
    String tenantName = "nonExistentTenant";
    when(tenantDao.getTenant(tenantName)).thenReturn(Optional.empty());

    // When
    Optional<Tenant> result = tenantManager.getTenant(tenantName);

    // Then
    assertThat(result).isEmpty();
    verify(tenantDao).getTenant(tenantName);
  }

  @Test
  void exists_when_tenant_exists_should_return_true() {
    // Given
    String tenantName = "existingTenant";
    Tenant existingTenant = ImmutableTenant.builder().value(tenantName).build();
    when(tenantDao.getTenant(tenantName)).thenReturn(Optional.of(existingTenant));

    // When
    boolean result = tenantManager.exists(tenantName);

    // Then
    assertThat(result).isTrue();
    verify(tenantDao).getTenant(tenantName);
  }

  @Test
  void exists_when_tenant_does_not_exist_should_return_false() {
    // Given
    String tenantName = "nonExistentTenant";
    when(tenantDao.getTenant(tenantName)).thenReturn(Optional.empty());

    // When
    boolean result = tenantManager.exists(tenantName);

    // Then
    assertThat(result).isFalse();
    verify(tenantDao).getTenant(tenantName);
  }
}