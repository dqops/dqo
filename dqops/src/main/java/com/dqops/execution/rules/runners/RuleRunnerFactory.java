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

import com.dqops.metadata.definitions.rules.RuleRunnerType;

/**
 * Rule runner factory.
 */
public interface RuleRunnerFactory {
    /**
     * Gets an instance of a rule runner given the class name.
     *
     * @param ruleRunnerType            Rule runner type.
     * @param ruleRunnerClassName Rule runner class name (optional, only for a custom java class).
     * @return Rule runner class name.
     */
    AbstractRuleRunner getRuleRunner(RuleRunnerType ruleRunnerType, String ruleRunnerClassName);

    /**
     * Gets an instance of a rule runner given the class name.
     * @param ruleRunnerClassName Rule runner class name.
     * @return Rule runner class name.
     */
    AbstractRuleRunner createCustomRuleRunnerByJavaClass(String ruleRunnerClassName);
}
