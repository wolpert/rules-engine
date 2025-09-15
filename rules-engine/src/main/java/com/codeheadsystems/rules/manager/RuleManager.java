package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.EventType;
import com.codeheadsystems.rules.model.Tenant;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RuleManager {

  @Inject
  public RuleManager() {
  }

  public List<String> globalRules() {
    return List.of("drl/global.drl");
  }

  public List<String> rulesFor(Tenant tenant) {
    return List.of("drl/tenant/" + tenant.name().toLowerCase() + ".drl");
  }

  public List<String> rulesFor(Tenant tenant, EventType eventType) {
    return List.of("drl/tenant/" + tenant.name() + "/" + eventType.name().toLowerCase() + ".drl");
  }

}
