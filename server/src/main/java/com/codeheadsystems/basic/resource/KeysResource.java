package com.codeheadsystems.basic.resource;

import com.codeheadsystems.api.basic.v1.Key;
import com.codeheadsystems.api.basic.v1.Keys;
import com.codeheadsystems.basic.converter.KeyConverter;
import com.codeheadsystems.basic.manager.KeyManager;
import com.codeheadsystems.basic.model.RawKey;
import com.codeheadsystems.server.resource.JerseyResource;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Keys resource.
 */
@Singleton
public class KeysResource implements Keys, JerseyResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeysResource.class);

  private final KeyManager keyManager;
  private final KeyConverter keyConverter;

  /**
   * Instantiates a new Keys resource.
   *
   * @param keyManager   the key manager
   * @param keyConverter the key converter
   */
  @Inject
  public KeysResource(final KeyManager keyManager,
                      final KeyConverter keyConverter) {
    LOGGER.info("KeysResource({},{})", keyManager, keyConverter);
    this.keyManager = keyManager;
    this.keyConverter = keyConverter;
  }

  @Override
  public Key read(final String uuid) {
    LOGGER.trace("get({})", uuid);
    final RawKey rawKey = keyManager.getRawKey(uuid);
    return keyConverter.from(rawKey);
  }

}
