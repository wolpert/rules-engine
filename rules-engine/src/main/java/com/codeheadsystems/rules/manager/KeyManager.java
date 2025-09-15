package com.codeheadsystems.rules.manager;

import com.codeheadsystems.metrics.declarative.Metrics;
import com.codeheadsystems.rules.model.ImmutableRawKey;
import com.codeheadsystems.rules.model.RawKey;
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

  /**
   * Instantiates a new Key manager.
   */
  @Inject
  public KeyManager() {
    LOGGER.info("KeyManager()");
  }

  /**
   * Gets raw key.
   *
   * @param uuid the uuid
   * @return the raw key
   */
  @Metrics
  public RawKey getRawKey(final String uuid) {
    LOGGER.trace("getRawKey({})", uuid);
    return ImmutableRawKey.builder()
        .uuid(uuid)
        .key("NotARealKey")
        .build();
  }

}
