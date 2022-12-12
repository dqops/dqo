/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.rules;

import ai.dqo.execution.ExecutionContext;

/**
 * Data quality rule runner that picks the right rule implementation and executes a rule.
 */
public interface DataQualityRuleRunner {
    /**
     * Executes a rule and returns the rule evaluation result.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param ruleRunParameters Rule run parameters (rule parameters, additional data that the rule requires).
     * @return Rule execution result with the severity status.
     */
    RuleExecutionResult executeRule(ExecutionContext executionContext, RuleExecutionRunParameters ruleRunParameters);
}
