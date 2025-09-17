package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Tenant;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

/**
 * The type Session manager.
 */
@Singleton
public class SessionManager {

  /**
   * Instantiates a new Session manager.
   */
  @Inject
  public SessionManager() {
  }

  /**
   * Gets kie session.
   *
   * @param tenant       the tenant
   * @param kieContainer the kie container
   * @return the kie session
   */
  public StatelessKieSession getKieSession(Tenant tenant, KieContainer kieContainer) {
    StatelessKieSession session = kieContainer.newStatelessKieSession();
    // TODO: Add in metrics holders here.
    return session;
  }

}
