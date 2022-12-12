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
package ai.dqo.execution.rules.runners.python;

import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.CheckExecutionContextObjectMother;
import ai.dqo.execution.rules.HistoricDataPoint;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.RuleExecutionRunParameters;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResultObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.utils.BeanFactoryObjectMother;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import org.springframework.beans.factory.BeanFactory;

import java.time.LocalDateTime;

/**
 * Object mother for the python rule runner. Also supports calling (evaluating) rules.
 */
public class PythonRuleRunnerObjectMother {
    /**
     * Returns the default python rule runner.
     * @return Python rule runner.
     */
    public static PythonRuleRunner getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(PythonRuleRunner.class);
    }

    /**
     * Executes a built-in rule for a single value.
     * @param actualValue Actual sensor value.
     * @param ruleParameters Rule parameters, also used to find the rule definition.
     * @return Rule evaluation result.
     */
    public static RuleExecutionResult executeBuiltInRule(double actualValue, AbstractRuleParametersSpec ruleParameters) {
        PythonRuleRunner ruleRunner = getDefault();
        ExecutionContext executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
        LocalDateTime today = LocalDateTimeTruncateUtility.truncateTimePeriod(LocalDateTime.now(), TimeSeriesGradient.DAY);
        RuleExecutionRunParameters ruleRunParameters = new RuleExecutionRunParameters(actualValue, ruleParameters, today, null, new RuleTimeWindowSettingsSpec());
        RuleDefinitionFindResult ruleDefinitionFindResult = RuleDefinitionFindResultObjectMother.findDqoHomeRuleDefinition(ruleParameters.getRuleDefinitionName());

        RuleExecutionResult ruleExecutionResult = ruleRunner.executeRule(executionContext, ruleRunParameters, ruleDefinitionFindResult);

        return ruleExecutionResult;
    }

    /**
     * Executes a built-in rule for a single value.
     * @param actualValue Actual sensor value.
     * @param ruleParameters Rule parameters, also used to find the rule definition.
     * @param readoutTimestamp Reading timestamp.
     * @param previousReadouts Array of previous readouts.
     * @param timeWindowSettingsSpec Time window settings.
     * @return Rule evaluation result.
     */
    public static RuleExecutionResult executeBuiltInRule(double actualValue,
														 AbstractRuleParametersSpec ruleParameters,
														 LocalDateTime readoutTimestamp,
														 HistoricDataPoint[] previousReadouts,
														 RuleTimeWindowSettingsSpec timeWindowSettingsSpec) {
        PythonRuleRunner ruleRunner = getDefault();
        ExecutionContext executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
        RuleExecutionRunParameters ruleRunParameters = new RuleExecutionRunParameters(actualValue, ruleParameters, readoutTimestamp, previousReadouts, timeWindowSettingsSpec);
        RuleDefinitionFindResult ruleDefinitionFindResult = RuleDefinitionFindResultObjectMother.findDqoHomeRuleDefinition(ruleParameters.getRuleDefinitionName());

        RuleExecutionResult ruleExecutionResult = ruleRunner.executeRule(executionContext, ruleRunParameters, ruleDefinitionFindResult);

        return ruleExecutionResult;
    }
}
