package com.codeheadsystems.rules.processor;

import com.codeheadsystems.rules.model.JsonObject;
import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityValue;
import java.util.Optional;

/**
 * The type Velocity processor.
 */
public class VelocityProcessor {

  private final VelocityDefinition definition;

  /**
   * Instantiates a new Velocity processor.
   *
   * @param definition the definition
   */
  public VelocityProcessor(VelocityDefinition definition) {
    this.definition = definition;
  }

  /**
   * From optional.
   *
   * @param <T>  the type parameter
   * @param json the json
   * @return the optional
   */
  public <T extends Number> Optional<VelocityValue<T>> from(JsonObject json) {
    final String varName = json.asString(definition.varPath());
    if (varName == null) {
      return Optional.empty();
    }
    throw new UnsupportedOperationException("UNFINISHED CODE");
  }

}
