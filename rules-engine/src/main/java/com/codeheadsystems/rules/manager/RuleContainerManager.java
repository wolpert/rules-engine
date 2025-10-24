package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableRuleExecutionContainer;
import com.codeheadsystems.rules.model.ImmutableRuleSession;
import com.codeheadsystems.rules.model.RuleExecutionContainer;
import com.codeheadsystems.rules.model.RuleExecutionRequest;
import com.codeheadsystems.rules.model.RuleSession;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.time.Duration;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Tenant container manager.
 */
@Singleton
public class RuleContainerManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleContainerManager.class);

  private final RuleManager ruleManager;
  private final LoadingCache<RuleExecutionRequest, RuleExecutionContainer> cache;

  /**
   * Instantiates a new Tenant container manager.
   *
   * @param ruleManager the rule manager
   */
  @Inject
  public RuleContainerManager(final RuleManager ruleManager) {
    this.ruleManager = ruleManager;
    var builder = Caffeine.newBuilder()
        .maximumSize(100)
        .refreshAfterWrite(Duration.ofSeconds(300)) // refresh from source every 60seconds
        .expireAfterAccess(Duration.ofSeconds(600)) // expire after 600 seconds of inactivity
        .removalListener(this::removalListener);
    this.cache = builder.build(this::internalRuleContainer);
    LOGGER.info("TenantContainerManager()");
  }

  /**
   * Removal listener.
   *
   * @param ruleExecutionRequest   the rule execution request
   * @param ruleExecutionContainer the tenant container
   * @param removalCause           the removal cause
   */
  public void removalListener(final RuleExecutionRequest ruleExecutionRequest,
                              final RuleExecutionContainer ruleExecutionContainer,
                              final RemovalCause removalCause) {
    LOGGER.info("Removing KieContainer for tenant:{} reason:{}", ruleExecutionRequest, removalCause);
    if (ruleExecutionContainer != null && ruleExecutionContainer.kieContainer() != null) {
      LOGGER.info("Disposing of KieContainer for tenant: {}", ruleExecutionContainer.ruleExecutionRequest());
      ruleExecutionContainer.kieContainer().dispose();
    }
  }

  /**
   * Tenant container tenant container.
   *
   * @param request the tenant
   * @return the tenant container
   */
  public RuleExecutionContainer ruleContainer(RuleExecutionRequest request) {
    return cache.get(request);
  }

  // TODO: This isn't actually correct, though it will be unique per tenant. Needs validation.
  private RuleExecutionContainer internalRuleContainer(final RuleExecutionRequest request) {
    LOGGER.info("Creating KieContainer for tenant: {}", request);
    final KieServices kieServices = KieServices.Factory.get();
    final ReleaseId releaseId = ruleManager.rulesFor(kieServices, request);
    final KieContainer kieContainer = containerize(kieServices, releaseId);
    return ImmutableRuleExecutionContainer.builder()
        .ruleExecutionRequest(request)
        .kieContainer(kieContainer)
        .build();
  }

  /**
   * Tenant rule session tenant rule session.
   *
   * @param request the request
   * @param facts   the facts
   * @return the tenant rule session
   */
  public RuleSession ruleSession(final RuleExecutionRequest request,
                                 final Facts facts) {
    final RuleExecutionContainer ruleExecutionContainer = ruleContainer(request);
    return ImmutableRuleSession.builder()
        .request(request)
        .container(ruleExecutionContainer)
        .facts(facts)
        .build();
  }

  /**
   * Containerize kie container.
   *
   * @param kieServices the kie services
   * @param releaseId   the release id
   * @return the kie container
   */
  public KieContainer containerize(final KieServices kieServices,
                                   final ReleaseId releaseId) {
    LOGGER.info("Container created for: {}", releaseId);
    final KieContainer kieContainer = kieServices.newKieContainer(releaseId);
    kieContainer.getKieBase().getKiePackages().forEach(p ->
        p.getRules().forEach(k -> LOGGER.info("\tPackage {} Rule: {}", p.getName(), k.getName()))
    );
    return kieContainer;
  }

}
