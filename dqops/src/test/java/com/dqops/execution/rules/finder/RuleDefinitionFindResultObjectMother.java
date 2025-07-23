/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
