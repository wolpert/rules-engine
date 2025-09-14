package com.codeheadsystems.basic.converter;

import com.codeheadsystems.api.basic.v1.ImmutableKey;
import com.codeheadsystems.api.basic.v1.Key;
import com.codeheadsystems.basic.model.RawKey;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Key converter.
 */
@Singleton
public class KeyConverter {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeyConverter.class);

  /**
   * Instantiates a new Key converter.
   */
  @Inject
  public KeyConverter() {
  }

  /**
   * From key.
   *
   * @param rawKey the raw key
   * @return the key
   */
  public Key from(final RawKey rawKey) {
    return ImmutableKey.builder().key(rawKey.key()).uuid(rawKey.uuid()).build();
  }


}
