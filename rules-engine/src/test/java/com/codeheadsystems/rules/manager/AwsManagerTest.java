package com.codeheadsystems.rules.manager;

import static com.codeheadsystems.rules.test.TestHelper.INTEG;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.model.ImmutableTableConfiguration;
import com.codeheadsystems.rules.model.TableConfiguration;
import com.codeheadsystems.rules.test.AWSLocalStackExtension;
import com.codeheadsystems.rules.test.TestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Tag(INTEG)
@ExtendWith(AWSLocalStackExtension.class)
class AwsManagerTest {

  @TestObject private DynamoDbClient client;
  private TableConfiguration tableConfiguration;

  @BeforeEach
  public void setup() {
    tableConfiguration = ImmutableTableConfiguration.builder().build();
  }

  @Test
  public void createTable() {
    assertThat(client.listTables().tableNames())
        .hasSize(1)
        .containsExactly(tableConfiguration.tableName());
  }

}