package com.codeheadsystems.rules.resource;

import com.codeheadsystems.api.rules.engine.v1.Tenant;
import com.codeheadsystems.rules.manager.TenantManager;
import com.codeheadsystems.server.resource.JerseyResource;
import jakarta.ws.rs.core.Response;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Tenant resource.
 */
@Singleton
public class TenantResource implements Tenant, JerseyResource {

  private final TenantManager tenantManager;

  /**
   * Instantiates a new Tenant resource.
   *
   * @param tenantManager the tenant manager
   */
  @Inject
  public TenantResource(final TenantManager tenantManager) {
    this.tenantManager = tenantManager;
  }

  @Override
  public Response read(final String tenant) {
    return tenantManager.exists(tenant)
        ? Response.ok(tenantManager.getOrCreateTenant(tenant)).build()
        : Response.status(Response.Status.NOT_FOUND).build();
  }
}
