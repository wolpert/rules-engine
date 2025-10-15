package com.codeheadsystems.rules.manager;

import static com.codeheadsystems.rules.TestHelper.INTEG;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.TestHelper;
import com.codeheadsystems.rules.model.ImmutableTableConfiguration;
import com.codeheadsystems.rules.model.TableConfiguration;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Tag(INTEG)
class AwsManagerTest {

  private DynamoDbClient client;
  private TableConfiguration tableConfiguration;

  @BeforeEach
  public void setup() throws URISyntaxException {
    tableConfiguration = ImmutableTableConfiguration.builder().build();
    client = TestHelper.withLocalstack(DynamoDbClient.builder()).build();
  }

  @Test
  public void createTable() {
    assertThat(client.listTables().tableNames())
        .hasSize(1)
        .containsExactly(tableConfiguration.tableName());
  }

}