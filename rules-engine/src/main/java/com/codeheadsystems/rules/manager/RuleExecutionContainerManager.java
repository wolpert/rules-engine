package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.model.Facts;
import com.codeheadsystems.rules.model.ImmutableRuleExecutionContainer;
import com.codeheadsystems.rules.model.ImmutableRuleSession;
import com.codeheadsystems.rules.model.RuleExecutionContainer;
import com.codeheadsystems.rules.model.RuleExecutionRequest;
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
public class RuleExecutionContainerManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionContainerManager.class);

  private final RuleManager ruleManager;
  private final FileAccessor fileAccessor;
  private final LoadingCache<RuleExecutionRequest, RuleExecutionContainer> cache;

  /**
   * Instantiates a new Tenant container manager.
   *
   * @param ruleManager  the rule manager
   * @param fileAccessor the file accessor
   */
  @Inject
  public RuleExecutionContainerManager(final RuleManager ruleManager, final FileAccessor fileAccessor) {
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
  public RuleExecutionContainer ruleExecutionContainer(RuleExecutionRequest request) {
    return cache.get(request);
  }

  // TODO: This isn't actually correct, though it will be unique per tenant. Needs validation.
  private RuleExecutionContainer internalRuleExecutionContainer(final RuleExecutionRequest request) {
    LOGGER.info("Creating KieContainer for tenant: {}", request);
    final Map<String, List<String>> ruleSet = ruleManager.rulesFor(request);
    final KieServices kieServices = KieServices.Factory.get();
    final ReleaseId releaseId = rulesFor(kieServices, request, ruleSet);
    final KieContainer kieContainer = containerize(kieServices, releaseId);
    return ImmutableRuleExecutionContainer.builder()
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
  public ReleaseId rulesFor(final KieServices kieServices, final RuleExecutionRequest request, final Map<String, List<String>> ruleSet) {
    final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    final ArrayList<InputStream> inputStreams = new ArrayList<>();

    ruleSet.forEach((type, list) -> {
          LOGGER.info("Loading rules: {}:{}", request, type);
          list.forEach(rule -> fileAccessor.getFile(rule).ifPresent(inputStream -> inputStreams.add(writeToFileSystem(rule, inputStream, kieFileSystem))));
        }
    );
    final ReleaseId releaseId = compile(kieServices, kieFileSystem);
    inputStreams.forEach(RuleExecutionContainerManager::close);
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
  public RuleSession ruleSession(final RuleExecutionRequest request,
                                 final Facts facts) {
    final RuleExecutionContainer ruleExecutionContainer = ruleExecutionContainer(request);
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
