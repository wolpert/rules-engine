package com.codeheadsystems.rules.manager;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.dao.RulesDao;
import com.codeheadsystems.rules.model.Rule;
import com.codeheadsystems.rules.model.RuleIdentifier;
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
   * @param rulesDao     the rules dao
   */
  @Inject
  public RuleManager(final FileAccessor fileAccessor,
                     final RulesDao rulesDao) {
    this.fileAccessor = fileAccessor;
    this.rulesDao = rulesDao;
    LOGGER.info("RuleManager({})", fileAccessor);
  }

  /**
   * Input stream optional.
   *
   * @param identifier the rule
   * @return the optional
   */
  public Optional<InputStream> inputStream(final RuleIdentifier identifier) {
    final String path = pathFor(identifier);
    return fileAccessor.getFile(path);
  }

  /**
   * Path for string.
   *
   * @param rule the rule
   * @return the string
   */
  public String pathFor(final Rule rule) {
    final RuleIdentifier identifier = rule.identifier();
    return pathFor(identifier);
  }

  public String pathFor(final RuleIdentifier identifier) {
    return String.format(RULE_PATH, identifier.tenant().value(), identifier.id(), identifier.version().value());
  }

  /**
   * Rule path map map.
   *
   * @param rules the rules
   * @return the map
   */
  public Map<Rule, String> rulePathMap(final List<Rule> rules) {
    return rules.stream().collect(Collectors.toMap(
        Function.identity(),
        this::pathFor,
        (existing, replacement) -> existing));
  }

  /**
   * Rules for list.
   *
   * @param ruleSetIdentifier the rule set identifier
   * @return the list
   */
  public List<RuleIdentifier> rulesFor(final RuleSetIdentifier ruleSetIdentifier) {
    return rulesDao.rulesFor(ruleSetIdentifier);
  }

}
