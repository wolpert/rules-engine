package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableTenantContainer;
import com.codeheadsystems.rules.model.ImmutableTenantRuleSession;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.TenantContainer;
import com.codeheadsystems.rules.model.TenantRuleSession;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.time.Duration;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class TenantContainerManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TenantContainerManager.class);

  private final RuleManager ruleManager;
  private final LoadingCache<Tenant, TenantContainer> cache;

  @Inject
  public TenantContainerManager(final RuleManager ruleManager) {
    this.ruleManager = ruleManager;
    var builder = Caffeine.newBuilder()
        .maximumSize(100)
        .refreshAfterWrite(Duration.ofSeconds(300)) // refresh from source every 60seconds
        .expireAfterAccess(Duration.ofSeconds(600)) // expire after 600 seconds of inactivity
        .removalListener(this::removalListener);
    this.cache = builder.build(this::internalTenantContainer);
    LOGGER.info("TenantContainerManager()");
  }

  public void removalListener(final Tenant tenant,
                              final TenantContainer tenantContainer,
                              final RemovalCause removalCause) {
    LOGGER.info("Removing KieContainer for tenant:{} reason:{}", tenant, removalCause);
    if (tenantContainer != null && tenantContainer.kieContainer() != null) {
      LOGGER.info("Disposing of KieContainer for tenant: {}", tenantContainer.tenant());
      tenantContainer.kieContainer().dispose();
    }
  }

  public TenantContainer tenantContainer(Tenant tenant) {
    return cache.get(tenant);
  }

  // TODO: This isn't actually correct, though it will be unique per tenant. Needs validation.
  private TenantContainer internalTenantContainer(final Tenant tenant) {
    LOGGER.info("Creating KieContainer for tenant: {}", tenant);
    final KieServices kieServices = KieServices.Factory.get();
    final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    ruleManager.rulesFor(tenant).forEach(rule ->
        kieFileSystem.write(ResourceFactory.newClassPathResource(rule)));
    final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
    kieBuilder.buildAll();
    if (kieBuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
      LOGGER.error("Errors: {}", kieBuilder.getResults().toString());
      throw new IllegalStateException("### errors ###");
    }
    final ReleaseId releaseId = kieBuilder.getKieModule().getReleaseId();
    LOGGER.info("Container created for {}: {}", tenant, releaseId);
    final KieContainer kieContainer = kieServices.newKieContainer(releaseId);
    kieContainer.getKieBase().getKiePackages().forEach(p ->
        p.getRules().forEach(k -> LOGGER.info("\tPackage {} Rule: {}", p.getName(), k.getName()))
    );
    return ImmutableTenantContainer.builder()
        .tenant(tenant)
        .kieContainer(kieContainer)
        .build();
  }

  public TenantRuleSession tenantRuleSession(final Tenant tenant,
                                             final Facts facts) {
    final TenantContainer tenantContainer = internalTenantContainer(tenant);
    return ImmutableTenantRuleSession.builder()
        .tenant(tenant)
        .container(tenantContainer)
        .facts(facts)
        .build();
  }

}
