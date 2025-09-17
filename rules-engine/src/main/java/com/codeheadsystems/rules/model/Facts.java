package com.codeheadsystems.rules.model;

import java.util.Set;
import org.immutables.value.Value;

/**
 * The interface Facts.
 */
@Value.Immutable
public interface Facts {

  /**
   * Event id string.
   *
   * @return the string
   */
  String eventId();

  /**
   * Json objects set.
   *
   * @return the set
   */
  Set<JsonObject> jsonObjects();

}
