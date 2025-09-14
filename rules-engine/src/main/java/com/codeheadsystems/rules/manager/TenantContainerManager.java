package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableTenantContainer;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.TenantContainer;
import com.codeheadsystems.rules.model.TenantRuleSession;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class TenantContainerManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TenantContainerManager.class);

  @Inject
  public TenantContainerManager() {
    LOGGER.info("TenantContainerManager()");
  }

  public TenantContainer tenantContainer(final Tenant tenant) {
    return ImmutableTenantContainer.builder().tenant(tenant).build();
  }

  public TenantRuleSession tenantRuleSession(final Tenant tenant,
                                             final Facts facts) {
    final TenantContainer tenantContainer = tenantContainer(tenant);
    return com.codeheadsystems.rules.model.ImmutableTenantRuleSession.builder()
        .tenant(tenant)
        .container(tenantContainer)
        .facts(facts)
        .build();
  }

}
