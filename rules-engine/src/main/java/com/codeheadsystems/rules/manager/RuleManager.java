package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.model.RuleExecutionRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Rule manager.
 */
@Singleton
public class RuleManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleManager.class);
  private static final String TENANT_RULES_PATH_FORMAT = "tenant/%s/%s/";
  private static final String GLOBAL_RULES_PATH = "global/%s/";

  private final FileAccessor fileAccessor;

  @Inject
  public RuleManager(final FileAccessor fileAccessor) {
    this.fileAccessor = fileAccessor;
    LOGGER.info("RuleManager({})", fileAccessor);
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
   * Rules for kie file system.
   *
   * @param request the tenantn rules request
   * @return the kie file system
   */
  public ReleaseId rulesFor(final KieServices kieServices, final RuleExecutionRequest request) {
    final List<String> globals = fileAccessor.listFiles(String.format(GLOBAL_RULES_PATH, request.globalRuleVersion().value()));
    final List<String> tenants = fileAccessor.listFiles(String.format(TENANT_RULES_PATH_FORMAT, request.tenant().name().toLowerCase(), request.tenantRuleVersion().value()));

    final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    final ArrayList<InputStream> inputStreams = new ArrayList<>();
    globals.forEach(rule -> {
      LOGGER.debug("Adding global rule {}:{}", request, rule);
      fileAccessor.getFile(rule).ifPresent(inputStream -> inputStreams.add(writeToFileSystem(rule, inputStream, kieFileSystem)));
    });
    tenants.forEach(rule -> {
      LOGGER.debug("Adding tenant rule {}:{}", request, rule);
      fileAccessor.getFile(rule).ifPresent(inputStream -> inputStreams.add(writeToFileSystem(rule, inputStream, kieFileSystem)));
    });
    final ReleaseId releaseId = compile(kieServices, kieFileSystem);
    inputStreams.forEach(RuleManager::close);
    return releaseId;
  }

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

}
