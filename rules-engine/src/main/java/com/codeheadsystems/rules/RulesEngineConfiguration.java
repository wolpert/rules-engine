package com.codeheadsystems.rules;

import com.codeheadsystems.rules.model.AwsConfiguration;
import com.codeheadsystems.server.ServerConfiguration;

/**
 * The type Keys server configuration.
 */
public class RulesEngineConfiguration extends ServerConfiguration {
  private AwsConfiguration awsConfiguration;

  /**
   * Instantiates a new Rules engine configuration.
   */
  public RulesEngineConfiguration() {
  }

  public AwsConfiguration getAwsConfiguration() {
    if (awsConfiguration == null) {
      awsConfiguration = AwsConfiguration.defaultConfiguration();
    }
    return awsConfiguration;
  }

  public void setAwsConfiguration(final AwsConfiguration awsConfiguration) {
    this.awsConfiguration = awsConfiguration;
  }
}
