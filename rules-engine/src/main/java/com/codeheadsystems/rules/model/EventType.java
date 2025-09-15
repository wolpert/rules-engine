package com.codeheadsystems.rules.model;

import org.immutables.value.Value;

/**
 * The interface Event type.
 */
@Value.Immutable
public interface EventType {


  /**
   * Name string.
   *
   * @return the string
   */
  String name();

}
