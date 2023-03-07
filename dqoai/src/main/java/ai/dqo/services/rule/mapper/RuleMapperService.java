package ai.dqo.services.rule.mapper;

import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.rest.models.metadata.RuleBasicModel;
import ai.dqo.rest.models.metadata.RuleModel;

public interface RuleMapperService {

    RuleDefinitionWrapper toRuleDefinitionWrapper(RuleBasicModel ruleBasicModel);

    RuleDefinitionWrapper toRuleDefinitionWrapper(RuleModel ruleModel);

    RuleModel toRuleModel(RuleDefinitionWrapper ruleDefinitionWrapper);
    RuleBasicModel toRuleBasicModel(RuleDefinitionWrapper ruleDefinitionWrapper);

}
