package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Tenant;
import javax.inject.Singleton;
import org.kie.api.builder.KieFileSystem;

/**
 * The type Rule manager.
 */
@Singleton
public class RuleManager {


  /**
   * Rules for kie file system.
   *
   * @param tenant the tenant
   * @return the kie file system
   */
  KieFileSystem rulesFor(Tenant tenant) {
    return null;
  }

}
