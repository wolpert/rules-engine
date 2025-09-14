package com.codeheadsystems.rules;

import com.codeheadsystems.rules.component.DaggerRuleEngineComponent;
import com.codeheadsystems.rules.module.AwsModule;
import com.codeheadsystems.server.Server;
import com.codeheadsystems.server.component.DropWizardComponent;
import com.codeheadsystems.server.module.DropWizardModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

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
    return DaggerRuleEngineComponent.builder()
        .awsModule(new AwsModule(DynamoDbClient.builder().region(Region.US_EAST_1).build()))
        .dropWizardModule(module)
        .build();
  }

}
