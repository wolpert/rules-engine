package com.codeheadsystems.rules.manager;

import io.github.robsonkades.uuidv7.UUIDv7;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Uuid manager, just to wrap it in case we want to swap out implementations.
 */
@Singleton
public class UuidManager {

  /**
   * Instantiates a new Uuid manager.
   */
  @Inject
  public UuidManager() {
  }

  /**
   * Generate uuid.
   *
   * @return the uuid
   */
  public UUID generate() {
    return UUIDv7.randomUUID();
  }

}
