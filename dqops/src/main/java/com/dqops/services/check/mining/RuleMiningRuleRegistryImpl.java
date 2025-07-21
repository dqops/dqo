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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registry of all rules that support rule mining. Classes that extend the {@link com.dqops.rules.AbstractRuleParametersSpec} class
 * and implement the {@link RuleMiningRule} interface are indexed by the rule name.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RuleMiningRuleRegistryImpl implements RuleMiningRuleRegistry {
    private final Map<String, RuleMiningRule> miningRules = new LinkedHashMap<>();

    /**
     * Dependency injection constructor that receives an array of all detected rules that support rule mining. Builds a registry of rules.
     * @param ruleMiningRules Rule mining rules.
     */
    @Autowired
    public RuleMiningRuleRegistryImpl(RuleMiningRule[] ruleMiningRules) {
        for (RuleMiningRule ruleMiningRule : ruleMiningRules) {
            this.miningRules.put(ruleMiningRule.getRuleDefinitionName(), ruleMiningRule);
        }
    }

    /**
     * Finds a rule mining rule in the rule registry.
     * @param ruleDefinitionName Rule definition name.
     * @return Rule mining rule when found or null.
     */
    @Override
    public RuleMiningRule getRule(String ruleDefinitionName) {
        return this.miningRules.get(ruleDefinitionName);
    }
}
