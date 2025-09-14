package com.codeheadsystems.rules;

import com.codeheadsystems.rules.component.DaggerRuleEngineComponent;
import com.codeheadsystems.server.Server;
import com.codeheadsystems.server.component.DropWizardComponent;
import com.codeheadsystems.server.module.DropWizardModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RulesEngineServer extends Server<RulesEngineConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RulesEngineServer.class);

  /**
   * Run the world.
   *
   * @param args from the command line.
   * @throws Exception if we could not start the server.
   */
  public static void main(String[] args) throws Exception {
    LOGGER.info("main({})", (Object) args);
    final RulesEngineServer server = new RulesEngineServer();
    server.run(args);
  }

  @Override
  protected DropWizardComponent dropWizardComponent(final DropWizardModule module) {
    return DaggerRuleEngineComponent.builder()
        .dropWizardModule(module)
        .build();
  }

}
