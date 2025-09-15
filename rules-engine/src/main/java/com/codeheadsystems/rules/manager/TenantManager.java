package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.dao.TenantDao;
import com.codeheadsystems.rules.model.EventType;
import com.codeheadsystems.rules.model.Tenant;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Tenant manager.
 */
@Singleton
public class TenantManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TenantManager.class);

  private final TenantDao tenantDao;

  /**
   * Instantiates a new Tenant manager.
   *
   * @param tenantDao the tenant dao
   */
  @Inject
  public TenantManager(final TenantDao tenantDao) {
    this.tenantDao = tenantDao;
    LOGGER.info("TenantManager()");
  }

  /**
   * Gets tenant.
   *
   * @param name the name
   * @return the tenant
   */
  public Tenant getTenant(String name) {
    return tenantDao.getTenant(name)
        .orElseGet(() -> tenantDao.create(name));
  }

  /**
   * Exists boolean.
   *
   * @param tenantName the tenant name
   * @return the boolean
   */
  public boolean exists(String tenantName) {
    return tenantDao.getTenant(tenantName).isPresent();
  }

  /**
   * Event type list.
   *
   * @param tenant the tenant
   * @return the list
   */
  public List<EventType> eventType(Tenant tenant) {
    return List.of();
  }
}
