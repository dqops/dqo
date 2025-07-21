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

/**
 * Rule definition search service. Finds the rule definition in the user come (custom definitions and overrides), then in the DQO_HOME (built-in rules).
 */
public interface RuleDefinitionFindService {
    /**
     * Finds a rule definition for a named rule.
     * @param executionContext Check execution context with access to the DQO_HOME and user home.
     * @param ruleName Rule name wiht or without the .py file extension.
     * @return Rule definition find result or null when the rule was not found.
     */
    RuleDefinitionFindResult findRule(ExecutionContext executionContext, String ruleName);
}
