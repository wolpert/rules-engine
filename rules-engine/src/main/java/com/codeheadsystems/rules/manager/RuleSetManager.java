package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.model.ImmutableRuleExecutionContainer;
import com.codeheadsystems.rules.model.ImmutableRuleSet;
import com.codeheadsystems.rules.model.RuleExecutionContainer;
import com.codeheadsystems.rules.model.RuleSet;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  private final FileAccessor fileAccessor;
  private final LoadingCache<RuleSetIdentifier, RuleExecutionContainer> cache;

  /**
   * Instantiates a new Tenant container manager.
   *
   * @param fileAccessor the file accessor
   */
  @Inject
  public RuleSetManager(final FileAccessor fileAccessor) {
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

  /**
   * Removal listener.
   *
   * @param ruleSetIdentifier the rule execution request
   * @param container         the tenant container
   * @param removalCause      the removal cause
   */
  public void removalListener(final RuleSetIdentifier ruleSetIdentifier,
                              final RuleExecutionContainer container,
                              final RemovalCause removalCause) {
    LOGGER.info("Removing KieContainer for tenant:{} reason:{}", ruleSetIdentifier, removalCause);
    if (container != null && container.kieContainer() != null) {
      LOGGER.info("Disposing of KieContainer for tenant: {}", container.ruleSet().ruleSetIdentifier());
      container.kieContainer().dispose();
    }
  }

  /**
   * Tenant container tenant container.
   *
   * @param request the tenant
   * @return the tenant container
   */
  public RuleExecutionContainer ruleExecutionContainer(RuleSetIdentifier request) {
    return cache.get(request);
  }

  // TODO: This isn't actually correct, though it will be unique per tenant. Needs validation.
  private RuleExecutionContainer internalRuleExecutionContainer(final RuleSetIdentifier request) {
    LOGGER.info("Creating KieContainer for tenant: {}", request);
    final KieServices kieServices = KieServices.Factory.get();
    final ReleaseId releaseId = rulesFor(kieServices, request);
    final KieContainer kieContainer = containerize(kieServices, releaseId);
    final RuleSet ruleSet = ImmutableRuleSet.builder()
        .ruleSetIdentifier(request)
        .build();
    return ImmutableRuleExecutionContainer.builder()
        .ruleSet(ruleSet)
        .kieContainer(kieContainer)
        .build();
  }

  /**
   * Rules for kie file system.
   *
   * @param kieServices the kie services
   * @param identifier  the tenantn rules request
   * @return the kie file system
   */
  public ReleaseId rulesFor(final KieServices kieServices,
                            final RuleSetIdentifier identifier) {
    LOGGER.info("Creating KieContainer for request: {}", identifier);
    final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    final ArrayList<InputStream> inputStreams = new ArrayList<>();
    final String ruleSetPath = fileAccessor.pathFor(identifier);
    final List<String> rulePaths = fileAccessor.listFiles(ruleSetPath);

    if (rulePaths.isEmpty()) {
      throw new IllegalArgumentException("No rules found for request: " + identifier);
    }

    rulePaths.forEach(path -> {
      Optional<InputStream> optionalInputStream = fileAccessor.getFile(path);
      optionalInputStream.ifPresent(inputStream -> {
        kieFileSystem.write(ResourceFactory.newInputStreamResource(inputStream).setSourcePath(path));
        LOGGER.info("Added rule to KieFileSystem: rule:{} [{}]", identifier, path);
        inputStreams.add(inputStream);
      });
    });
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
