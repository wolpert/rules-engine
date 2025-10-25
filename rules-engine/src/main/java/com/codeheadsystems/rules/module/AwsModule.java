package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.model.AwsConfiguration;
import com.codeheadsystems.rules.model.TableConfiguration;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

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
   * @param configuration the configuration
   * @return the table configuration
   */
  @Provides
  @Singleton
  public TableConfiguration tableConfiguration(RulesEngineConfiguration configuration) {
    return configuration.getTableConfiguration();
  }

  /**
   * Aws configuration aws configuration.
   *
   * @param configuration the configuration
   * @return the aws configuration
   */
  @Provides
  @Singleton
  public AwsConfiguration awsConfiguration(RulesEngineConfiguration configuration) {
    return configuration.getAwsConfiguration();
  }

  /**
   * Dynamo db client dynamo db client.
   *
   * @param configuration the rules engine configuration
   * @return the dynamo db client
   */
  @Provides
  @Singleton
  public DynamoDbClient dynamoDbClient(AwsConfiguration configuration) {
    return DynamoDbClient.builder().region(Region.of(configuration.region())).build();
  }

  /**
   * S 3 client s 3 client.
   *
   * @param configuration the configuration
   * @return the s 3 client
   */
  @Provides
  @Singleton
  public S3Client s3Client(AwsConfiguration configuration) {
    return S3Client.builder().region(Region.of(configuration.region())).build();
  }

}
