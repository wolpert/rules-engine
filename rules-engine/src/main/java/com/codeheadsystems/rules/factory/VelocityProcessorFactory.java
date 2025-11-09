package com.codeheadsystems.rules.factory;

import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.processor.JsonVelocityProcessor;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorDecimal;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorEmptyDecimal;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorEmptyInteger;
import com.codeheadsystems.rules.processor.impl.JsonVelocityProcessorInteger;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Velocity processor factory.
 */
@Singleton
public class VelocityProcessorFactory {

  /**
   * Instantiates a new Velocity processor factory.
   */
  @Inject
  public VelocityProcessorFactory() {
  }

  /**
   * Create velocity processor.
   *
   * @param velocityDefinition the velocity definition
   * @return the velocity processor
   */
  public JsonVelocityProcessor<?> create(VelocityDefinition velocityDefinition) {
    if (velocityDefinition.valPath().isEmpty()) {
      return switch (velocityDefinition.type()) {
        case INTEGER -> new JsonVelocityProcessorEmptyInteger(velocityDefinition);
        case DECIMAL -> new JsonVelocityProcessorEmptyDecimal(velocityDefinition);
      };
    }
    return switch (velocityDefinition.type()) {
      case INTEGER -> new JsonVelocityProcessorInteger(velocityDefinition);
      case DECIMAL -> new JsonVelocityProcessorDecimal(velocityDefinition);
    };
  }

}
