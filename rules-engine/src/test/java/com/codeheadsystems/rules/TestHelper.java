package com.codeheadsystems.rules;

import com.codeheadsystems.rules.model.TableConfiguration;
import java.util.UUID;
import org.testcontainers.utility.DockerImageName;

public class TestHelper {

  public static TableConfiguration uniqueTableName() {
    return com.codeheadsystems.rules.model.ImmutableTableConfiguration.builder()
        .tableName("rules_table_" + UUID.randomUUID())
        .build();
  }

  public static final DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:4.8.0");
}
