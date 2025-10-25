package com.codeheadsystems.rules.dao;

import com.codeheadsystems.rules.model.Rule;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DynamoDbRulesDao implements RulesDao {

  @Inject
  public DynamoDbRulesDao() {
  }

  @Override
  public List<Rule> rulesFor(final RuleSetIdentifier ruleSetIdentifier) {
    return List.of();
  }
}
