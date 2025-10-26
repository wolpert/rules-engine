package com.codeheadsystems.rules.dao;

import com.codeheadsystems.rules.model.Rule;
import com.codeheadsystems.rules.model.RuleIdentifier;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.util.List;

/**
 * The interface Rules dao.
 */
public interface RulesDao {

  /**
   * Rules for list.
   *
   * @param ruleSetIdentifier the rule set identifier
   * @return the list
   */
  List<RuleIdentifier> rulesFor(final RuleSetIdentifier ruleSetIdentifier);

}
