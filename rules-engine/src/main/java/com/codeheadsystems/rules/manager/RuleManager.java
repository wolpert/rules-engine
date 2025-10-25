package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.dao.RulesDao;
import com.codeheadsystems.rules.model.Rule;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
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
  // RULE_PATH must not start with a '/'.
  private static final String RULE_PATH = "%s/%s/%s.drl";

  private final FileAccessor fileAccessor;
  private final RulesDao rulesDao;

  /**
   * Instantiates a new Rule manager.
   *
   * @param fileAccessor the file accessor
   */
  @Inject
  public RuleManager(final FileAccessor fileAccessor,
                     final RulesDao rulesDao) {
    this.fileAccessor = fileAccessor;
    this.rulesDao = rulesDao;
    LOGGER.info("RuleManager({})", fileAccessor);
  }

  public Optional<InputStream> inputStream(final Rule rule) {
    final String path = pathFor(rule);
    return fileAccessor.getFile(path);
  }

  public String pathFor(final Rule rule) {
    return String.format(RULE_PATH, rule.tenant().value(), rule.id(), rule.version().value());
  }

  public Map<Rule, String> rulePathMap(final List<Rule> rules) {
    return rules.stream().collect(Collectors.toMap(
        Function.identity(),
        this::pathFor,
        (existing, replacement) -> existing));
  }

  public List<Rule> rulesFor(final RuleSetIdentifier ruleSetIdentifier) {
    return rulesDao.rulesFor(ruleSetIdentifier);
  }

}
