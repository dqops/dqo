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
