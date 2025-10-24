package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableRuleSet;
import com.codeheadsystems.rules.model.ImmutableRuleSession;
import com.codeheadsystems.rules.model.RuleSet;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import com.codeheadsystems.rules.model.RuleSession;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

/**
 * The type Tenant container manager.
 */
@Singleton
public class RuleSetManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleSetManager.class);

  private final RuleManager ruleManager;
  private final FileAccessor fileAccessor;
  private final LoadingCache<RuleSetIdentifier, RuleSet> cache;

  /**
   * Instantiates a new Tenant container manager.
   *
   * @param ruleManager  the rule manager
   * @param fileAccessor the file accessor
   */
  @Inject
  public RuleSetManager(final RuleManager ruleManager, final FileAccessor fileAccessor) {
    this.ruleManager = ruleManager;
    this.fileAccessor = fileAccessor;
    var builder = Caffeine.newBuilder()
        .maximumSize(100)
        .refreshAfterWrite(Duration.ofSeconds(300)) // refresh from source every 60seconds
        .expireAfterAccess(Duration.ofSeconds(600)) // expire after 600 seconds of inactivity
        .removalListener(this::removalListener);
    this.cache = builder.build(this::internalRuleExecutionContainer);
    LOGGER.info("TenantContainerManager()");
  }

  private static void close(InputStream inputStream) {
    try {
      inputStream.close();
    } catch (Exception e) {
      LOGGER.warn("Unable to close input stream: {}", e.getMessage());
    }
  }

  // used so we can get the inputstream to close later.
  private static InputStream writeToFileSystem(final String rule, final InputStream inputStream, final KieFileSystem kieFileSystem) {
    kieFileSystem.write(ResourceFactory.newInputStreamResource(inputStream).setSourcePath(rule));
    return inputStream;
  }

  /**
   * Removal listener.
   *
   * @param ruleSetIdentifier   the rule execution request
   * @param ruleSet the tenant container
   * @param removalCause           the removal cause
   */
  public void removalListener(final RuleSetIdentifier ruleSetIdentifier,
                              final RuleSet ruleSet,
                              final RemovalCause removalCause) {
    LOGGER.info("Removing KieContainer for tenant:{} reason:{}", ruleSetIdentifier, removalCause);
    if (ruleSet != null && ruleSet.kieContainer() != null) {
      LOGGER.info("Disposing of KieContainer for tenant: {}", ruleSet.ruleExecutionRequest());
      ruleSet.kieContainer().dispose();
    }
  }

  /**
   * Tenant container tenant container.
   *
   * @param request the tenant
   * @return the tenant container
   */
  public RuleSet ruleExecutionContainer(RuleSetIdentifier request) {
    return cache.get(request);
  }

  // TODO: This isn't actually correct, though it will be unique per tenant. Needs validation.
  private RuleSet internalRuleExecutionContainer(final RuleSetIdentifier request) {
    LOGGER.info("Creating KieContainer for tenant: {}", request);
    final Map<String, List<String>> ruleSet = ruleManager.rulesFor(request);
    final KieServices kieServices = KieServices.Factory.get();
    final ReleaseId releaseId = rulesFor(kieServices, request, ruleSet);
    final KieContainer kieContainer = containerize(kieServices, releaseId);
    return ImmutableRuleSet.builder()
        .ruleExecutionRequest(request)
        .kieContainer(kieContainer)
        .build();
  }

  /**
   * Rules for kie file system.
   *
   * @param kieServices the kie services
   * @param request     the tenantn rules request
   * @param ruleSet     the rule set
   * @return the kie file system
   */
  public ReleaseId rulesFor(final KieServices kieServices, final RuleSetIdentifier request, final Map<String, List<String>> ruleSet) {
    final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    final ArrayList<InputStream> inputStreams = new ArrayList<>();

    ruleSet.forEach((type, list) -> {
          LOGGER.info("Loading rules: {}:{}", request, type);
          list.forEach(rule -> fileAccessor.getFile(rule).ifPresent(inputStream -> inputStreams.add(writeToFileSystem(rule, inputStream, kieFileSystem))));
        }
    );
    final ReleaseId releaseId = compile(kieServices, kieFileSystem);
    inputStreams.forEach(RuleSetManager::close);
    return releaseId;
  }

  /**
   * Compile release id.
   *
   * @param kieServices   the kie services
   * @param kieFileSystem the kie file system
   * @return the release id
   */
  public ReleaseId compile(final KieServices kieServices,
                           final KieFileSystem kieFileSystem) {
    LOGGER.info("compile()");
    final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
    kieBuilder.buildAll();
    if (kieBuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
      LOGGER.error("Errors: {}", kieBuilder.getResults().toString());
      throw new IllegalStateException("### errors ###");
    }
    final ReleaseId releaseId = kieBuilder.getKieModule().getReleaseId();
    LOGGER.info("Compiled KieModule: {}", releaseId);
    return releaseId;
  }

  /**
   * Tenant rule session tenant rule session.
   *
   * @param request the request
   * @param facts   the facts
   * @return the tenant rule session
   */
  public RuleSession ruleSession(final RuleSetIdentifier request,
                                 final Facts facts) {
    final RuleSet ruleSet = ruleExecutionContainer(request);
    return ImmutableRuleSession.builder()
        .request(request)
        .container(ruleSet)
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
