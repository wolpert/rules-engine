package com.codeheadsystems.rules;

import com.codeheadsystems.server.ServerConfiguration;

/**
 * The type Keys server configuration.
 */
public class RulesEngineConfiguration extends ServerConfiguration {

  private String region;

  public String getRegion() {
    return region;
  }

  public void setRegion(final String region) {
    this.region = region;
  }

}
