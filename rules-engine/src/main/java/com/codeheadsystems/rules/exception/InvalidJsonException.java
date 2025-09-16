package com.codeheadsystems.rules.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * The type Invalid json exception.
 *
 * TODO: Create an exception mapper for this 4xx level exception.
 */
public class InvalidJsonException extends RuntimeException {

  /**
   * Instantiates a new Invalid json exception.
   *
   * @param string the string
   * @param e      the e
   */
  public InvalidJsonException(final String string, final JsonProcessingException e) {
    super(string, e);
  }
}
