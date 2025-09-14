package com.codeheadsystems.basic.model;

import org.immutables.value.Value;

/**
 * RawKey is a value-object for the byte array that represents a decoded key.
 */
@Value.Immutable
public interface RawKey {


  /**
   * Uuid uuid.
   *
   * @return the uuid
   */
  String uuid();

  /**
   * Key.
   *
   * @return byte array.
   */
  String key();

}
