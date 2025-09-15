package com.codeheadsystems.rules.model;

import java.util.Set;
import org.immutables.value.Value;

/**
 * The interface Facts.
 */
@Value.Immutable
public interface Facts {

  /**
   * Json objects set.
   *
   * @return the set
   */
  Set<JsonObject> jsonObjects();

}
