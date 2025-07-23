/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.runners;

import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.RuleExecutionRunParameters;
import com.dqops.execution.rules.finder.RuleDefinitionFindResult;

/**
 * Abstract base class for rule runners that are evaluating rules to detect if a sensor value is valid or an alert should be raised.
 */
public abstract class AbstractRuleRunner {
    /**
     * Executes a rule that evaluates a value and checks if it is valid.
     * @param executionContext Check execution context with access to the DQO_HOME and user home.
     * @param ruleRunParameters Rule run parameters with the values to be sent to the rule as parameters.
     * @param ruleDefinitionFindResult Rule definition find result to identity a rule (like a python module) that will be executed.
     * @return Rule evaluation result.
     */
    public abstract RuleExecutionResult executeRule(ExecutionContext executionContext,
                                                    RuleExecutionRunParameters ruleRunParameters,
                                                    RuleDefinitionFindResult ruleDefinitionFindResult);
}
