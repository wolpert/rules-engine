package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Version;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Global rules version manager. Stubbed out for now.
 */
@Singleton
public class GlobalRulesVersionManager {

  private static final Version ACTIVE_VERSION = Version.of("1.0");

  @Inject
  public GlobalRulesVersionManager() {
  }

  public Version getActiveVersion() {
    return ACTIVE_VERSION;
  }

}
