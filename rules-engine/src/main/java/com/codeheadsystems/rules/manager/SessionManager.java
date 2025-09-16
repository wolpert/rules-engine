package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Tenant;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

@Singleton
public class SessionManager {

  @Inject
  public SessionManager() {
  }

  public StatelessKieSession getKieSession(Tenant tenant, KieContainer kieContainer) {
    StatelessKieSession session = kieContainer.newStatelessKieSession();
    // TODO: Add in metrics holders here.
    return session;
  }

}
