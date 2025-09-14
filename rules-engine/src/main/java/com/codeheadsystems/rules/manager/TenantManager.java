package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.dao.TenantDao;
import com.codeheadsystems.rules.model.Tenant;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TenantManager {

  private final TenantDao tenantDao;

  @Inject
  public TenantManager(final TenantDao tenantDao) {
    this.tenantDao = tenantDao;
  }

  public Tenant getTenant(String name) {
    return tenantDao.getTenant(name)
        .orElseGet(() -> tenantDao.create(name));
  }

  public boolean exists(String tenantName) {
    return tenantDao.getTenant(tenantName).isPresent();
  }
}
