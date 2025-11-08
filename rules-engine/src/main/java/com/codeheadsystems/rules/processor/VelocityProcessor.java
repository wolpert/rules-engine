package com.codeheadsystems.rules.processor;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValue;
import com.codeheadsystems.rules.model.VelocityVariableName;
import java.util.Optional;

/**
 * The interface Velocity processor.
 *
 * @param <T> the type parameter
 */
public abstract class VelocityProcessor<T extends Number> {

  /**
   * The Val path.
   */
  protected final String valPath;
  /**
   * The Var name path.
   */
  protected final String varNamePath;

  /**
   * Instantiates a new Velocity processor.
   *
   * @param velocityDefinition the velocity definition
   */
  public VelocityProcessor(VelocityDefinition velocityDefinition) {
    this.valPath = velocityDefinition.valPath().orElse(null);
    this.varNamePath = velocityDefinition.varPath();
  }

  /**
   * Value from optional.
   *
   * @param json the json
   * @return the optional
   */
  public abstract Optional<VelocityValue<T>> valueFrom(JsonObject json);

  /**
   * Variable name from optional.
   *
   * @param json the json
   * @return the optional
   */
  public Optional<VelocityVariableName> variableNameFrom(final JsonObject json) {
    if (json.exists(varNamePath)) {
      return Optional.of(VelocityVariableName.of(json.asString(varNamePath)));
    } else {
      return Optional.empty();
    }
  }

}
