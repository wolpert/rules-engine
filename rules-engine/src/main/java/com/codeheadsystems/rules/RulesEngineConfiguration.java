package com.codeheadsystems.rules;

import com.codeheadsystems.rules.model.AwsConfiguration;
import com.codeheadsystems.rules.model.ImmutableTableConfiguration;
import com.codeheadsystems.rules.model.TableConfiguration;
import com.codeheadsystems.server.ServerConfiguration;

/**
 * The type Keys server configuration.
 */
public class RulesEngineConfiguration extends ServerConfiguration {


  private String s3RulePrefix = "/rules/";
  private AwsConfiguration awsConfiguration;
  private TableConfiguration tableConfiguration;

  /**
   * Instantiates a new Rules engine configuration.
   */
  public RulesEngineConfiguration() {
  }

  /**
   * Gets aws configuration.
   *
   * @return the aws configuration
   */
  public AwsConfiguration getAwsConfiguration() {
    if (awsConfiguration == null) {
      awsConfiguration = AwsConfiguration.defaultConfiguration();
    }
    return awsConfiguration;
  }

  /**
   * Sets aws configuration.
   *
   * @param awsConfiguration the aws configuration
   */
  public void setAwsConfiguration(final AwsConfiguration awsConfiguration) {
    this.awsConfiguration = awsConfiguration;
  }

  /**
   * Gets s 3 rule prefix.
   *
   * @return the s 3 rule prefix
   */
  public String getS3RulePrefix() {
    return s3RulePrefix;
  }

  /**
   * Sets s 3 rule prefix.
   *
   * @param s3RulePrefix the s 3 rule prefix
   */
  public void setS3RulePrefix(final String s3RulePrefix) {
    this.s3RulePrefix = s3RulePrefix;
  }

  /**
   * Gets table configuration.
   *
   * @return the table configuration
   */
  public TableConfiguration getTableConfiguration() {
    if (tableConfiguration == null) {
      tableConfiguration = ImmutableTableConfiguration.builder().build();
    }
    return tableConfiguration;
  }

  /**
   * Sets table configuration.
   *
   * @param tableConfiguration the table configuration
   */
  public void setTableConfiguration(final TableConfiguration tableConfiguration) {
    this.tableConfiguration = tableConfiguration;
  }
}
