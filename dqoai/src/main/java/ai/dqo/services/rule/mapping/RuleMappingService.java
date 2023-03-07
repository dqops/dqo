package ai.dqo.services.rule.mapping;

import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.rest.models.metadata.RuleBasicModel;
import ai.dqo.rest.models.metadata.RuleModel;

/**
 * Interface for mapping a RuleBasicModel (REST model) object to a RuleDefinitionWrapper object.
 */
public interface RuleMappingService {

    RuleDefinitionWrapper toRuleDefinitionWrapper(RuleBasicModel ruleBasicModel);
    RuleDefinitionSpec withRuleDefinitionSpec(RuleBasicModel ruleBasicModel);

    FileContent withRuleDefinitionPythonModuleContent(RuleBasicModel ruleBasicModel);

    RuleDefinitionWrapper toRuleDefinitionWrapper(RuleModel ruleModel);

    RuleModel toRuleModel(RuleDefinitionWrapper ruleDefinitionWrapper);
    RuleBasicModel toRuleBasicModel(RuleDefinitionWrapper ruleDefinitionWrapper);

}
