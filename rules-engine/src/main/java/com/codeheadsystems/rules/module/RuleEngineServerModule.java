package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.accessor.impl.S3FileAccessor;
import com.codeheadsystems.rules.factory.ObjectMapperFactory;
import com.codeheadsystems.rules.model.ExecutionEnvironment;
import com.codeheadsystems.rules.model.ImmutableExecutionEnvironment;
import com.codeheadsystems.rules.resource.InvalidJsonExceptionMapper;
import com.codeheadsystems.rules.resource.TenantResource;
import com.codeheadsystems.server.ServerConfiguration;
import com.codeheadsystems.server.resource.JerseyResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Binds;
import dagger.BindsOptionalOf;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.Optional;
import javax.inject.Singleton;

/**
 * The type Keys server module.
 */
@Module(includes = RuleEngineServerModule.Binder.class)
public class RuleEngineServerModule {

  private final Optional<FileAccessor> fileAccessor;

  /**
   * Instantiates a new Rule engine server module.
   */
  public RuleEngineServerModule() {
    this(null);
  }

  public RuleEngineServerModule(FileAccessor fileAccessor) {
    this.fileAccessor = Optional.ofNullable(fileAccessor);
  }

  /**
   * Clock clock.
   *
   * @return the clock
   */
  @Provides
  @Singleton
  public Clock clock() {
    return Clock.systemUTC();
  }

  /**
   * Object mapper object mapper.
   *
   * @param objectMapperFactory the object mapper factory
   * @return the object mapper
   */
  @Provides
  @Singleton
  public ObjectMapper objectMapper(final ObjectMapperFactory objectMapperFactory) {
    return objectMapperFactory.objectMapper();
  }

  /**
   * Secure random secure random.
   *
   * @return the secure random
   */
  @Provides
  @Singleton
  public SecureRandom secureRandom() {
    return new SecureRandom();
  }

  /**
   * Basic server configuration rules engine configuration.
   *
   * @param configuration the configuration
   * @return the rules engine configuration
   */
  @Provides
  @Singleton
  public RulesEngineConfiguration basicServerConfiguration(ServerConfiguration configuration) {
    return (RulesEngineConfiguration) configuration;
  }

  /**
   * Execution environment execution environment.
   *
   * @param configuration the configuration
   * @return the execution environment
   */
  @Provides
  @Singleton
  public ExecutionEnvironment executionEnvironment(RulesEngineConfiguration configuration) {
    return ImmutableExecutionEnvironment.builder().value(configuration.getStage()).build();
  }

  @Provides
  @Singleton
  public FileAccessor fileAccessor(S3FileAccessor s3FileAccessor) {
    return fileAccessor.orElse(s3FileAccessor);
  }


  /**
   * The interface Binder.
   */
  @Module
  interface Binder {

    /**
     * Tenant resource jersey resource.
     *
     * @param resource the resource
     * @return the jersey resource
     */
    @Binds
    @IntoSet
    JerseyResource tenantResource(TenantResource resource);

    /**
     * invalid key mapper.
     *
     * @param resource resource.
     * @return JerseyResource. jersey resource
     */
    @Binds
    @IntoSet
    JerseyResource invalidKeyExceptionMapper(InvalidJsonExceptionMapper resource);

  }

}
