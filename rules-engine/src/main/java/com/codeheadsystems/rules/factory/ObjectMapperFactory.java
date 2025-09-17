package com.codeheadsystems.rules.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Object mapper factory.
 */
@Singleton
public class ObjectMapperFactory {

  /**
   * Instantiates a new Object mapper factory.
   */
  @Inject
  public ObjectMapperFactory() {
  }


  /**
   * O object mapper object mapper.
   *
   * @return the object mapper
   */
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new Jdk8Module());
  }

}
