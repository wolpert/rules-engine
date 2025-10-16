package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.model.ImmutableTableConfiguration;
import com.codeheadsystems.rules.model.TableConfiguration;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * The type Aws module.
 */
@Module
public class AwsModule {

  /**
   * Instantiates a new Aws module.
   */
  public AwsModule() {
  }


  /**
   * Table details table configuration.
   *
   * @return the table configuration
   */
  @Provides
  @Singleton
  public TableConfiguration tableDetails() {
    return ImmutableTableConfiguration.builder().build();
  }

  /**
   * Dynamo db client dynamo db client.
   *
   * @param rulesEngineConfiguration the rules engine configuration
   * @return the dynamo db client
   */
  @Provides
  @Singleton
  public DynamoDbClient dynamoDbClient(RulesEngineConfiguration rulesEngineConfiguration) {
    return DynamoDbClient.builder().region(Region.of(rulesEngineConfiguration.getRegion())).build();
  }

}
