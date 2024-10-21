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
package com.dqops.execution.rules.runners.python;

import com.dqops.execution.CheckExecutionContextObjectMother;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.HistoricDataPoint;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.RuleExecutionRunParameters;
import com.dqops.execution.rules.finder.RuleDefinitionFindResult;
import com.dqops.execution.rules.finder.RuleDefinitionFindResultObjectMother;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
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
     * @param expectedValue Optional expected value.
     * @param ruleParameters Rule parameters, also used to find the rule definition.
     * @return Rule evaluation result.
     */
    public static RuleExecutionResult executeBuiltInRule(Double actualValue, Double expectedValue, AbstractRuleParametersSpec ruleParameters) {
        PythonRuleRunner ruleRunner = getDefault();
        ExecutionContext executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
        LocalDateTime today = LocalDateTimeTruncateUtility.truncateTimePeriod(LocalDateTime.now(), TimePeriodGradient.day);
        RuleDefinitionFindResult ruleDefinitionFindResult = RuleDefinitionFindResultObjectMother.findDqoHomeRuleDefinition(ruleParameters.getRuleDefinitionName());
        RuleExecutionRunParameters ruleRunParameters = new RuleExecutionRunParameters(actualValue, expectedValue,
                ruleParameters, today, null, new RuleTimeWindowSettingsSpec(), ruleParameters.getRuleParametersTemplate(), null);

        RuleExecutionResult ruleExecutionResult = ruleRunner.executeRule(executionContext, ruleRunParameters, ruleDefinitionFindResult);

        return ruleExecutionResult;
    }

    /**
     * Executes a built-in rule for a single value.
     * @param actualValue Actual sensor value.
     * @param ruleParameters Rule parameters, also used to find the rule definition.
     * @return Rule evaluation result.
     */
    public static RuleExecutionResult executeBuiltInRule(Double actualValue,  AbstractRuleParametersSpec ruleParameters) {
        return executeBuiltInRule(actualValue, null, ruleParameters);
    }

    /**
     * Executes a built-in rule for a single value.
     * @param actualValue Actual sensor value.
     * @param expectedValue Optional expected value.
     * @param ruleParameters Rule parameters, also used to find the rule definition.
     * @param readoutTimestamp Reading timestamp.
     * @param previousReadouts Array of previous readouts.
     * @param timeWindowSettingsSpec Time window settings.
     * @return Rule evaluation result.
     */
    public static RuleExecutionResult executeBuiltInRule(Double actualValue,
                                                         Double expectedValue,
														 AbstractRuleParametersSpec ruleParameters,
														 LocalDateTime readoutTimestamp,
														 HistoricDataPoint[] previousReadouts,
														 RuleTimeWindowSettingsSpec timeWindowSettingsSpec) {
        PythonRuleRunner ruleRunner = getDefault();
        ExecutionContext executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
        RuleDefinitionFindResult ruleDefinitionFindResult = RuleDefinitionFindResultObjectMother.findDqoHomeRuleDefinition(ruleParameters.getRuleDefinitionName());
        RuleExecutionRunParameters ruleRunParameters = new RuleExecutionRunParameters(actualValue, expectedValue,
                ruleParameters, readoutTimestamp, previousReadouts, timeWindowSettingsSpec, ruleParameters.getRuleParametersTemplate(), null);

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
    public static RuleExecutionResult executeBuiltInRule(Double actualValue,
                                                         AbstractRuleParametersSpec ruleParameters,
                                                         LocalDateTime readoutTimestamp,
                                                         HistoricDataPoint[] previousReadouts,
                                                         RuleTimeWindowSettingsSpec timeWindowSettingsSpec) {
        return executeBuiltInRule(actualValue, null, ruleParameters, readoutTimestamp, previousReadouts, timeWindowSettingsSpec);
    }
}
