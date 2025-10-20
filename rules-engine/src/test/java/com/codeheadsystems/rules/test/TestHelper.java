package com.codeheadsystems.rules.test;

import java.net.URI;
import java.net.URISyntaxException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

public class TestHelper {

  public static final String INTEG = "integ";

  public static <B extends AwsClientBuilder<B, C>, C> B withLocalstack(final B awsClient) throws URISyntaxException {
    return awsClient.endpointOverride(new URI("http://localhost:4566"))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create("ACCESS_KEY", "SECRET_KEY")
            )
        )
        .region(Region.of("us-east-1"));
  }

  public static S3Client s3Client() {
    try {
      return withLocalstack(S3Client.builder().forcePathStyle(true)).build();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public static DynamoDbClient dynamoDbClient() {
    try {
      return withLocalstack(DynamoDbClient.builder()).build();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

}
