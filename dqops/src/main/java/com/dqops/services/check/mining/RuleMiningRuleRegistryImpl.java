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
