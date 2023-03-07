package ai.dqo.services.rule;

import ai.dqo.rest.models.metadata.RuleBasicModel;

import java.util.List;

public interface RuleRestService {

    List<RuleBasicModel> getAllBuiltInRules();

    RuleBasicModel getBuiltInRule(String ruleName);

    List<RuleBasicModel> getAllCustomInRules();

    RuleBasicModel getCustomRule(String ruleName);

    void createRule(RuleBasicModel ruleBasicModel);

    void updateCustomRule(RuleBasicModel ruleBasicModel);

    List<RuleBasicModel> getAllCombinedRules();

    RuleBasicModel getCombinedRule();
}
