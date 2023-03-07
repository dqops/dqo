package ai.dqo.services.rule;

import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.rest.models.metadata.RuleBasicModel;

import java.util.List;

public interface RuleService {

    List<RuleBasicModel> getAllBuiltInRules();

    RuleBasicModel getBuiltInRule(String ruleName);

    RuleBasicModel getAllCustomInRules();

    RuleBasicModel getCustomRule(String name);

    void createRule(RuleBasicModel ruleBasicModel);

    void updateCustomRule(RuleBasicModel ruleBasicModel);

    List<RuleBasicModel> getAllCombinedRules();

    RuleBasicModel getCombinedRule();
}
