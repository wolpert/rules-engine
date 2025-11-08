package com.codeheadsystems.rules.processor;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.model.VelocityVariableName;
import java.util.Optional;

/**
 * The interface Velocity processor.
 *
 * @param <T> the type parameter
 */
public interface VelocityProcessor<T extends Number> {


  /**
   * Value from optional.
   *
   * @param json the json
   * @return the optional
   */
  Optional<VelocityValue<T>> valueFrom(JsonObject json);

  Optional<VelocityVariableName> variableNameFrom(JsonObject json);

}
