package com.codeheadsystems.rules.manager;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.model.ImmutableTableConfiguration;
import com.codeheadsystems.rules.model.TableConfiguration;
import com.codeheadsystems.test.datastore.DataStore;
import com.codeheadsystems.test.datastore.DynamoDbExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

@ExtendWith(DynamoDbExtension.class)
class AwsManagerTest {

  @DataStore private DynamoDbClient client;
  private final TableConfiguration tableConfiguration = ImmutableTableConfiguration.builder().build();
  private AwsManager manager;

  @BeforeEach
  public void setup() {
    manager = new AwsManager(client, tableConfiguration);
  }

  @AfterEach
  public void cleanup() {
    client.deleteTable(DeleteTableRequest.builder()
        .tableName(tableConfiguration.tableName())
        .build());
  }

  @Test
  public void createTable() {
    manager.createTable();

    assertThat(client.listTables().tableNames())
        .hasSize(1)
        .containsExactly(tableConfiguration.tableName());
  }

}