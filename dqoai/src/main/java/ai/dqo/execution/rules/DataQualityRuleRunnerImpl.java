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
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.finder.RuleDefinitionFindService;
import ai.dqo.execution.rules.runners.AbstractRuleRunner;
import ai.dqo.execution.rules.runners.RuleRunnerFactory;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Data quality rule runner that picks the right rule implementation and executes a rule.
 */
@Component
public class DataQualityRuleRunnerImpl implements DataQualityRuleRunner {
    private final RuleDefinitionFindService ruleDefinitionFindService;
    private final RuleRunnerFactory ruleRunnerFactory;

    /**
     * Creates a rule runner that uses a rule finder to call the correct rule runner implementation.
     * @param ruleDefinitionFindService Rule definition finder.
     * @param ruleRunnerFactory Rule runner factory to create actual runner implementations.
     */
    @Autowired
    public DataQualityRuleRunnerImpl(RuleDefinitionFindService ruleDefinitionFindService, RuleRunnerFactory ruleRunnerFactory) {
        this.ruleDefinitionFindService = ruleDefinitionFindService;
        this.ruleRunnerFactory = ruleRunnerFactory;
    }

    /**
     * Executes a rule and returns the rule evaluation result.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param ruleRunParameters Rule run parameters (rule parameters, additional data that the rule requires).
     * @param sensorRunParameters Sensor run parameters with the reference to the check.
     * @return Rule execution result with the severity status.
     */
    @Override
    public RuleExecutionResult executeRule(ExecutionContext executionContext,
                                           RuleExecutionRunParameters ruleRunParameters,
                                           SensorExecutionRunParameters sensorRunParameters) {
        String ruleName = ruleRunParameters.getParameters().getRuleDefinitionName(sensorRunParameters.getCheck());

        RuleDefinitionFindResult ruleFindResult = this.ruleDefinitionFindService.findRule(executionContext, ruleName);
        RuleDefinitionSpec ruleDefinitionSpec = ruleFindResult.getRuleDefinitionSpec();
        AbstractRuleRunner ruleRunner = this.ruleRunnerFactory.getRuleRunner(ruleDefinitionSpec.getType(),
                ruleDefinitionSpec.getJavaClassName());

        RuleExecutionResult result = ruleRunner.executeRule(executionContext, ruleRunParameters, ruleFindResult);
        return result;
    }
}
