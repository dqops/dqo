package ai.dqo.rules;

import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;

/**
 * Object mother that returns rule time windows from the real rule configuration files.
 */
public class RuleTimeWindowSettingsSpecObjectMother {
    /**
     * Finds the given rule and returns the time window configuration for the rule.
     * @param ruleName Rule name.
     * @return Time window setting for rules that need historic data.
     */
    public static RuleTimeWindowSettingsSpec getRealTimeWindowSettings(String ruleName) {
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        RuleDefinitionList ruleDefinitionList = dqoHomeContext.getDqoHome().getRules();
        RuleDefinitionWrapper ruleDefinitionWrapper = ruleDefinitionList.getByObjectName(ruleName, true);
        return ruleDefinitionWrapper.getSpec().getTimeWindow();
    }
}
