package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.model.RuleExecutionRequest;
import com.codeheadsystems.rules.model.Tenant;
import com.codeheadsystems.rules.model.Version;
import java.util.List;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
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
  public RuleManager(final FileAccessor fileAccessor, final GlobalRulesVersionManager globalRulesVersionManager) {
    this.fileAccessor = fileAccessor;
    LOGGER.info("RuleManager({})", fileAccessor);
  }

  /**
   * Rules for kie file system.
   *
   * @param request the tenantn rules request
   * @return the kie file system
   */
  KieFileSystem rulesFor(final KieServices kieServices, final RuleExecutionRequest request) {


    final List<String> globals = fileAccessor.listFiles(String.format(GLOBAL_RULES_PATH, request.globalRuleVersion().value()));
    final List<String> tenants = fileAccessor.listFiles(String.format(TENANT_RULES_PATH_FORMAT, request.tenant().name().toLowerCase(), request.tenantRuleVersion().value()));

    final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    globals.forEach(rule -> {
      LOGGER.debug("Adding global rule {}:{}", request, rule);
      fileAccessor.getFile(rule).ifPresent(inputStream ->
          kieFileSystem.write(ResourceFactory.newInputStreamResource(inputStream).setSourcePath(rule))
      );
    });
    tenants.forEach(rule -> {
      LOGGER.debug("Adding tenant rule {}:{}", request, rule);
      fileAccessor.getFile(rule).ifPresent(inputStream ->
          kieFileSystem.write(ResourceFactory.newInputStreamResource(inputStream).setSourcePath(rule))
      );
    });
    return kieFileSystem;
  }

}
