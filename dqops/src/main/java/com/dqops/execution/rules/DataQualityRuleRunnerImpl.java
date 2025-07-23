/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules;

import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.finder.RuleDefinitionFindResult;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.rules.runners.AbstractRuleRunner;
import com.dqops.execution.rules.runners.RuleRunnerFactory;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.rules.RuleSeverityLevel;
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
        String ruleName = sensorRunParameters.getEffectiveSensorRuleNames().getRuleName();

        RuleDefinitionFindResult ruleFindResult = this.ruleDefinitionFindService.findRule(executionContext, ruleName);
        RuleDefinitionSpec ruleDefinitionSpec = ruleFindResult.getRuleDefinitionSpec();
        AbstractRuleRunner ruleRunner = this.ruleRunnerFactory.getRuleRunner(ruleDefinitionSpec.getType(),
                ruleDefinitionSpec.getJavaClassName());

        RuleExecutionResult result = ruleRunner.executeRule(executionContext, ruleRunParameters, ruleFindResult);
        return result;
    }
}
