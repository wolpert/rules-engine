package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.model.ImmutableTableDetails;
import com.codeheadsystems.rules.model.TableDetails;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Module
public class AwsModule {

  private final DynamoDbClient dynamoDbClient;

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
  public DynamoDbClient dynamoDbClient() {
    return dynamoDbClient;
  }

}
