package com.codeheadsystems.basic;

import com.codeheadsystems.basic.component.DaggerKeysServerComponent;
import com.codeheadsystems.server.Server;
import com.codeheadsystems.server.component.DropWizardComponent;
import com.codeheadsystems.server.module.DropWizardModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicServer extends Server<BasicServerConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicServer.class);

  /**
   * Run the world.
   *
   * @param args from the command line.
   * @throws Exception if we could not start the server.
   */
  public static void main(String[] args) throws Exception {
    LOGGER.info("main({})", (Object) args);
    final BasicServer server = new BasicServer();
    server.run(args);
  }

  @Override
  protected DropWizardComponent dropWizardComponent(final DropWizardModule module) {
    return DaggerKeysServerComponent.builder()
        .dropWizardModule(module)
        .build();
  }

}
