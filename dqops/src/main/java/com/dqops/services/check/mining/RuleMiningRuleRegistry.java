/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining;

/**
 * Registry of all rules that support rule mining. Classes that extend the {@link com.dqops.rules.AbstractRuleParametersSpec} class
 * and implement the {@link RuleMiningRule} interface are indexed by the rule name.
 */
public interface RuleMiningRuleRegistry {
    /**
     * Finds a rule mining rule in the rule registry.
     *
     * @param ruleDefinitionName Rule definition name.
     * @return Rule mining rule when found or null.
     */
    RuleMiningRule getRule(String ruleDefinitionName);
}
