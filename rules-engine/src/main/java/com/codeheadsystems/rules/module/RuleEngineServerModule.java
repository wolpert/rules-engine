package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.factory.ObjectMapperFactory;
import com.codeheadsystems.rules.resource.InvalidKeyExceptionMapper;
import com.codeheadsystems.rules.resource.KeysResource;
import com.codeheadsystems.rules.resource.TenantResource;
import com.codeheadsystems.server.ServerConfiguration;
import com.codeheadsystems.server.resource.JerseyResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import java.security.SecureRandom;
import java.time.Clock;
import javax.inject.Singleton;

/**
 * The type Keys server module.
 */
@Module(includes = RuleEngineServerModule.Binder.class)
public class RuleEngineServerModule {

  /**
   * Clock clock.
   *
   * @return the clock
   */
  @Provides
  @Singleton
  Clock clock() {
    return Clock.systemUTC();
  }

  @Provides
  @Singleton
  ObjectMapper objectMapper(final ObjectMapperFactory objectMapperFactory) {
    return objectMapperFactory.oObjectMapper();
  }

  /**
   * Secure random secure random.
   *
   * @return the secure random
   */
  @Provides
  @Singleton
  SecureRandom secureRandom() {
    return new SecureRandom();
  }

  @Provides
  @Singleton
  RulesEngineConfiguration basicServerConfiguration(ServerConfiguration configuration) {
    return (RulesEngineConfiguration) configuration;
  }

  /**
   * The interface Binder.
   */
  @Module
  interface Binder {

    /**
     * Keys resource jersey resource.
     *
     * @param resource the resource
     * @return the jersey resource
     */
    @Binds
    @IntoSet
    JerseyResource keysResource(KeysResource resource);

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
    JerseyResource invalidKeyExceptionMapper(InvalidKeyExceptionMapper resource);

  }

}
