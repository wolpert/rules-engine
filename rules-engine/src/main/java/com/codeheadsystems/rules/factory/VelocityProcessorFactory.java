package com.codeheadsystems.rules.factory;

import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.processor.VelocityProcessor;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorDecimal;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorEmptyDecimal;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorEmptyInteger;
import com.codeheadsystems.rules.processor.impl.VelocityProcessorInteger;
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
  public VelocityProcessor<?> create(VelocityDefinition velocityDefinition) {
    if (velocityDefinition.valPath().isEmpty()) {
      return switch (velocityDefinition.type()) {
        case INTEGER -> new VelocityProcessorEmptyInteger();
        case DECIMAL -> new VelocityProcessorEmptyDecimal();
      };
    }
    String path = velocityDefinition.valPath().get();
    return switch (velocityDefinition.type()) {
      case INTEGER -> new VelocityProcessorInteger(path);
      case DECIMAL -> new VelocityProcessorDecimal(path);
    };
  }

}
