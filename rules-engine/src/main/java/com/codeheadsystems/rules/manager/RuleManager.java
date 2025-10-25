package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
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
  private static final String GLOBAL = "GLOBAL";
  private static final String TENANT = "TENANT";

  private final FileAccessor fileAccessor;

  /**
   * Instantiates a new Rule manager.
   *
   * @param fileAccessor the file accessor
   */
  @Inject
  public RuleManager(final FileAccessor fileAccessor) {
    this.fileAccessor = fileAccessor;
    LOGGER.info("RuleManager({})", fileAccessor);
  }

  /**
   * Rules for map.
   *
   * @param request the request
   * @return the map
   */
  public Map<String, List<String>> rulesFor(final RuleSetIdentifier request) {
    return Map.of(
        GLOBAL, fileAccessor.listFiles(String.format(GLOBAL_RULES_PATH, request.globalRuleVersion().value())),
        TENANT, fileAccessor.listFiles(String.format(TENANT_RULES_PATH_FORMAT, request.tenant().value().toLowerCase(), request.tenantRuleVersion().value()))
    );
  }

}
