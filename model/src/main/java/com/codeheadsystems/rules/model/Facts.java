package com.codeheadsystems.rules.model;

import java.util.Set;
import org.immutables.value.Value;

/**
 * The interface Facts.
 *
 * @param <T> the type parameter
 */
@Value.Immutable
public interface Facts <T> {

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
  Set<T> jsonObjects();

}
