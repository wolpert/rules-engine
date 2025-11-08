package com.codeheadsystems.rules.test;

import com.codeheadsystems.rules.factory.JsonObjectFactory;
import com.codeheadsystems.rules.factory.ObjectMapperFactory;
import com.codeheadsystems.rules.model.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class FixtureHelper {

  public static String fixture(String path) {
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(path)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("File not found: " + path);
      }
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static JsonObject jsonObject(String path) {
    String json = fixture(path);
    final ObjectMapper mapper = new ObjectMapperFactory().objectMapper();
    return new JsonObjectFactory(mapper).jsonObject(json);
  }

}
