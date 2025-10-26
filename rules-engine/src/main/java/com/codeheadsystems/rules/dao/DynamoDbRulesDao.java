package com.codeheadsystems.rules.dao;

import com.codeheadsystems.rules.model.RuleIdentifier;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Dynamo db rules dao.
 */
@Singleton
public class DynamoDbRulesDao implements RulesDao {

  /**
   * Instantiates a new Dynamo db rules dao.
   */
  @Inject
  public DynamoDbRulesDao() {
  }

  @Override
  public List<RuleIdentifier> rulesFor(final RuleSetIdentifier ruleSetIdentifier) {
    return List.of();
  }
}
