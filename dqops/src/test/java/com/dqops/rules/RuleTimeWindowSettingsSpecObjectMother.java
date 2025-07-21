/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules;

import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;

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
