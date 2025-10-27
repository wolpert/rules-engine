package com.codeheadsystems.rules.accessor;

import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * The interface File accessor.
 */
public interface FileAccessor {

  String RULE_SET_PATH = "%s/events/%s/ruleset/%s/";

  default String pathFor(final RuleSetIdentifier identifier) {
    return String.format(RULE_SET_PATH, identifier.tenant().value(), identifier.event().value(), identifier.eventVersion().value());
  }

  /**
   * List files list.
   *
   * @param path the path
   * @return the list
   */
  List<String> listFiles(String path);

  /**
   * Gets file.
   *
   * @param path the path
   * @return the file
   */
  Optional<InputStream> getFile(String path);

}
