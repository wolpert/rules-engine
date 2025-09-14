package com.codeheadsystems.basic.integ;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.api.basic.v1.Key;
import com.codeheadsystems.basic.BasicServer;
import com.codeheadsystems.basic.BasicServerConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class KeyCreationTest {

  private static final DropwizardAppExtension<BasicServerConfiguration> EXT = new DropwizardAppExtension<>(
      BasicServer.class,
      ResourceHelpers.resourceFilePath("server-test.yml")
  );

  @Test
  void get() {
    final UUID uuid = UUID.randomUUID();
    final Response response = EXT.client().target("http://localhost:" + EXT.getLocalPort() + "/v1/basic/" + uuid)
        .request()
        .get();
    assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("status", 200);
    final Key key = response.readEntity(Key.class);
    assertThat(key)
        .hasFieldOrProperty("uuid");
  }

  @Test
  void badRequest() {
    final Response response = EXT.client().target("http://localhost:" + EXT.getLocalPort() + "/v1/bad")
        .request()
        .get();
    assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("status", 404);
  }
}
