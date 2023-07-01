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
package com.dqops.execution.rules.finder;

import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;

/**
 * Object mother for finding rule definitions.
 */
public class RuleDefinitionFindResultObjectMother {
    /**
     * Finds a rule definition in the default DQO_HOME. Can find only built-in sensor definitions.
     * @param ruleName Rule name.
     * @return Rule definition retrieved from the DQO_HOME.
     */
    public static RuleDefinitionFindResult findDqoHomeRuleDefinition(String ruleName) {
        RuleDefinitionFindService ruleDefinitionFindService = RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService();
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();

        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);
        RuleDefinitionFindResult ruleDefinitionFindResult = ruleDefinitionFindService.findRule(executionContext, ruleName);
        return ruleDefinitionFindResult;
    }

    /**
     * Finds a rule definition searching in both the provided user home context and the default DQO_HOME.
     * @param userHomeContext User home context to search for the rule definition.
     * @param ruleName Rule name.
     * @return Rule definition retrieved from the user home or DQO_HOME (whichever has the definition first).
     */
    public static RuleDefinitionFindResult findRuleDefinition(UserHomeContext userHomeContext, String ruleName) {
        RuleDefinitionFindService ruleDefinitionFindService = RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();

        ExecutionContext executionContext = new ExecutionContext(userHomeContext, dqoHomeContext);
        RuleDefinitionFindResult ruleDefinitionFindResult = ruleDefinitionFindService.findRule(executionContext, ruleName);
        return ruleDefinitionFindResult;
    }
}
