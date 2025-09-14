package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.model.ImmutableTableDetails;
import com.codeheadsystems.rules.model.TableDetails;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Module
public class AwsModule {

  private final DynamoDbClient dynamoDbClient;

  public AwsModule() {
    this(null);
  }

  public AwsModule(final DynamoDbClient dynamoDbClient) {
    this.dynamoDbClient = dynamoDbClient;
  }

  @Provides
  @Singleton
  public TableDetails tableDetails() {
    return ImmutableTableDetails.builder().build();
  }

  @Provides
  @Singleton
  public DynamoDbClient dynamoDbClient(RulesEngineConfiguration rulesEngineConfiguration) {
    if (dynamoDbClient == null) {
      return DynamoDbClient.builder().region(Region.of(rulesEngineConfiguration.getRegion())).build();
    }
    return dynamoDbClient;
  }

}
