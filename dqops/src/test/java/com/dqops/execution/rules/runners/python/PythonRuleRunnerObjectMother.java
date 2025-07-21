/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.runners.python;

import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.execution.CheckExecutionContextObjectMother;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.HistoricDataPoint;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.RuleExecutionRunParameters;
import com.dqops.execution.rules.RuleModelUpdateMode;
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
                ruleParameters, today, CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME,
                null, new RuleTimeWindowSettingsSpec(), ruleParameters.getRuleParametersTemplate(), null, RuleModelUpdateMode.when_outdated);

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
                ruleParameters, readoutTimestamp, CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME,
                previousReadouts, timeWindowSettingsSpec, ruleParameters.getRuleParametersTemplate(), null, RuleModelUpdateMode.when_outdated);

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
