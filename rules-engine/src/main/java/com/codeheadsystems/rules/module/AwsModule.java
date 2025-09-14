package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.model.ImmutableTableConfiguration;
import com.codeheadsystems.rules.model.TableConfiguration;
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
  public TableConfiguration tableDetails() {
    return ImmutableTableConfiguration.builder().build();
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
