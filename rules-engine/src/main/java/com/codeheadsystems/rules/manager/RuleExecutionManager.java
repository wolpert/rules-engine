package com.codeheadsystems.rules.manager;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RuleExecutionManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionManager.class);

  @Inject
  public RuleExecutionManager() {
    LOGGER.info("RuleExecutionManager()");
  }

}
