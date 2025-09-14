package com.codeheadsystems.rules.manager;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.TestHelper;
import com.codeheadsystems.rules.model.TableConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

@Testcontainers
@Tag("integration")
class AwsManagerTest {


  @Container
  private static final LocalStackContainer localstack = new LocalStackContainer(TestHelper.localstackImage)
      .withServices(LocalStackContainer.Service.DYNAMODB);

  private DynamoDbClient client;
  private TableConfiguration tableConfiguration;
  private AwsManager manager;

  @BeforeEach
  public void setup() {
    tableConfiguration = TestHelper.uniqueTableName();
    client = DynamoDbClient.builder()
        .endpointOverride(localstack.getEndpoint())
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())
            )
        )
        .region(Region.of(localstack.getRegion()))
        .build();
    manager = new AwsManager(client, tableConfiguration);
  }

  @AfterEach
  public void cleanup() {
    client.deleteTable(DeleteTableRequest.builder()
        .tableName(tableConfiguration.tableName())
        .build());
    tableConfiguration = null;
    client = null;
  }

  @Test
  public void createTable() {
    manager.createTable();

    assertThat(client.listTables().tableNames())
        .hasSize(1)
        .containsExactly(tableConfiguration.tableName());
  }

}