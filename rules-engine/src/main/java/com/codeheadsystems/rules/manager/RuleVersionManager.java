package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.Version;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Global rules version manager. Stubbed out for now.
 */
@Singleton
public class RuleVersionManager {

  private static final Version ACTIVE_VERSION = Version.of("1.0");

  /**
   * Instantiates a new Rules version manager.
   */
  @Inject
  public RuleVersionManager() {
  }

  /**
   * Gets active global version.
   *
   * @return the active global version
   */
  public Version getActiveGlobalVersion() {
    return ACTIVE_VERSION;
  }

  /**
   * Gets active tenant version.
   *
   * @param tenant the tenant
   * @return the active tenant version
   */
  public Version getActiveTenantVersion(Tenant tenant) {
    return ACTIVE_VERSION;
  }

}
