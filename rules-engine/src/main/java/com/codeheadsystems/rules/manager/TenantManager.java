package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.dao.TenantDao;
import com.codeheadsystems.rules.model.Tenant;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class TenantManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TenantManager.class);

  private final TenantDao tenantDao;

  @Inject
  public TenantManager(final TenantDao tenantDao) {
    this.tenantDao = tenantDao;
    LOGGER.info("TenantManager()");
  }

  public Tenant getTenant(String name) {
    return tenantDao.getTenant(name)
        .orElseGet(() -> tenantDao.create(name));
  }

  public boolean exists(String tenantName) {
    return tenantDao.getTenant(tenantName).isPresent();
  }
}
