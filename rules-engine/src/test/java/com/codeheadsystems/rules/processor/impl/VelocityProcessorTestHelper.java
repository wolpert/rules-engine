package com.codeheadsystems.rules.processor.impl;

import com.codeheadsystems.rules.model.VelocityDefinition;
import com.codeheadsystems.rules.model.VelocityIdentifier;
import com.codeheadsystems.rules.model.Window;
import java.util.Optional;

public class VelocityProcessorTestHelper {

  public static VelocityDefinition createVelocityDefinition(String valPath, String varPath) {
    return new VelocityDefinition() {
      @Override
      public Optional<String> valPath() {
        return Optional.ofNullable(valPath);
      }


      @Override
      public String varPath() {
        return varPath;
      }

      ///  ignore below

      @Override
      public VelocityIdentifier identifier() {
        return null;
      }

      @Override
      public String name() {
        return "";
      }

      @Override
      public Optional<String> description() {
        return Optional.empty();
      }

      @Override
      public Window window() {
        return null;
      }

      @Override
      public String varName() {
        return "";
      }
    };
  }

}
