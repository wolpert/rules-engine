package com.codeheadsystems.rules.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ObjectMapperFactory {

  @Inject
  public ObjectMapperFactory() {
  }


  public ObjectMapper oObjectMapper() {
    return new ObjectMapper()
        .registerModule(new Jdk8Module());
  }

}
