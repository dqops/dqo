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
package ai.dqo.execution.rules.runners;

import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.RuleExecutionRunParameters;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;

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
