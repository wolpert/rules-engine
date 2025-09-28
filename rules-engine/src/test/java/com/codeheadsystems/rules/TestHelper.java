package com.codeheadsystems.rules;

import java.net.URI;
import java.net.URISyntaxException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.regions.Region;

public class TestHelper {

  public static <B extends AwsClientBuilder<B, C>, C> B withLocalstack(final B awsClient) throws URISyntaxException {
    return awsClient.endpointOverride(new URI("http://localhost:4566"))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create("ACCESS_KEY", "SECRET_KEY")
            )
        )
        .region(Region.of("us-east-1"));
  }

}
