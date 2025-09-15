package com.codeheadsystems.rules;

import com.codeheadsystems.server.ServerConfiguration;

/**
 * The type Keys server configuration.
 */
public class RulesEngineConfiguration extends ServerConfiguration {
  private String region;

  /**
   * Instantiates a new Rules engine configuration.
   */
  public RulesEngineConfiguration() {
  }

  /**
   * Gets region.
   *
   * @return the region
   */
  public String getRegion() {
    return region;
  }

  /**
   * Sets region.
   *
   * @param region the region
   */
  public void setRegion(final String region) {
    this.region = region;
  }

}
