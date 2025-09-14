package com.codeheadsystems.rules.component;

import com.codeheadsystems.rules.module.AwsModule;
import com.codeheadsystems.rules.module.RuleEngineServerModule;
import com.codeheadsystems.server.component.DropWizardComponent;
import com.codeheadsystems.server.module.DropWizardModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Creates the pieces needed for the control plane to run. Required.
 */
@Component(modules = {
    AwsModule.class,
    DropWizardModule.class,
    RuleEngineServerModule.class,
})
@Singleton
public interface RuleEngineComponent extends DropWizardComponent {
}
