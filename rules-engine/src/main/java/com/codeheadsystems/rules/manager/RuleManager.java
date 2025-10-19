package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Tenant;
import org.kie.api.builder.KieFileSystem;

public interface RuleManager {

  KieFileSystem rulesFor(Tenant tenant);

}
