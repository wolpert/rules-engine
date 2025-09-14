package com.codeheadsystems.basic.module;

import com.codeheadsystems.basic.resource.InvalidKeyExceptionMapper;
import com.codeheadsystems.basic.resource.KeysResource;
import com.codeheadsystems.server.resource.JerseyResource;
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
@Module(includes = KeysServerModule.Binder.class)
public class KeysServerModule {

  /**
   * The constant LIQUIBASE_SETUP_XML.
   */
  public static final String LIQUIBASE_SETUP_XML = "liquibase/liquibase-setup.xml";

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
