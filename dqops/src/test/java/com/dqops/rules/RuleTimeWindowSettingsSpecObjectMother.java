/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
