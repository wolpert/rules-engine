package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.EventType;
import com.codeheadsystems.rules.model.Tenant;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Rule manager.
 */
@Singleton
public class RuleManager {

  /**
   * Instantiates a new Rule manager.
   */
  @Inject
  public RuleManager() {
  }

  /**
   * Global rules list.
   *
   * @return the list
   */
  public List<String> globalRules() {
    return List.of("drl/global.drl");
  }

  /**
   * Rules for list.
   *
   * @param tenant the tenant
   * @return the list
   */
  public List<String> rulesFor(Tenant tenant) {
    return List.of("drl/tenant/" + tenant.name().toLowerCase() + ".drl");
  }

  /**
   * Rules for list.
   *
   * @param tenant    the tenant
   * @param eventType the event type
   * @return the list
   */
  public List<String> rulesFor(Tenant tenant, EventType eventType) {
    return List.of("drl/tenant/" + tenant.name() + "/" + eventType.name().toLowerCase() + ".drl");
  }

}
