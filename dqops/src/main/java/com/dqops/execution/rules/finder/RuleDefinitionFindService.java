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
