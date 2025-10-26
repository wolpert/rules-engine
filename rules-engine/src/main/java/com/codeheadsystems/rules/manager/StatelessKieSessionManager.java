package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.RuleSetIdentifier;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

/**
 * The type Session manager.
 */
@Singleton
public class StatelessKieSessionManager {

  /**
   * Instantiates a new Session manager.
   */
  @Inject
  public StatelessKieSessionManager() {
  }

  /**
   * Gets kie session.
   *
   * @param request      the request
   * @param kieContainer the kie container
   * @return the kie session
   */
  public StatelessKieSession getKieSession(RuleSetIdentifier request, KieContainer kieContainer) {
    StatelessKieSession session = kieContainer.newStatelessKieSession();
    // TODO: Add in metrics holders here.
    return session;
  }

}
