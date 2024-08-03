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
