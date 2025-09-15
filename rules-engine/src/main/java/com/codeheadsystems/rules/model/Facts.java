package com.codeheadsystems.rules.model;

import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
public interface Facts {

  Set<JsonObject> jsonObjects();

}
