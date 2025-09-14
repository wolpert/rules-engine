package com.codeheadsystems.basic.manager;

import com.codeheadsystems.basic.model.ImmutableRawKey;
import com.codeheadsystems.basic.model.RawKey;
import com.codeheadsystems.metrics.declarative.Metrics;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Key manager.
 */
@Singleton
public class KeyManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeyManager.class);

  @Inject
  public KeyManager() {
    LOGGER.info("KeyManager()");
  }

  @Metrics
  public RawKey getRawKey(final String uuid) {
    LOGGER.trace("getRawKey({})", uuid);
    return ImmutableRawKey.builder()
        .uuid(uuid)
        .key("NotARealKey")
        .build();
  }

}
