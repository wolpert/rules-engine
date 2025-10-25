package com.codeheadsystems.rules.dao;

import com.codeheadsystems.rules.model.Rule;
import com.codeheadsystems.rules.model.RuleSetIdentifier;
import java.util.List;

public interface RulesDao {

  List<Rule> rulesFor(final RuleSetIdentifier ruleSetIdentifier);

}
